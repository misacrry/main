package cn.ruishan.common.security;

import cn.hutool.core.util.StrUtil;
import cn.ruishan.common.utils.ResponseUtil;
import cn.ruishan.common.web.ApiResult;
import cn.ruishan.common.web.JsonResult;
import cn.ruishan.iac.service.IacUserService;
import cn.ruishan.main.entity.BaseUser;
import cn.ruishan.main.entity.LoginType;
import cn.ruishan.main.mapper.BaseUserMapper;
import cn.ruishan.sys.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @desc: 登录过滤器
 * @author: longgang.lei
 * @time: 2021-04-05 12:57
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 登陆类型
     */
    public static final String SPRING_SECURITY_FORM_LOGIN_TYPE_KEY = "loginType";

    private String loginTypeParameter = SPRING_SECURITY_FORM_LOGIN_TYPE_KEY;

    /**
     * 是否对密码进行加密
     */
    public static final String SPRING_SECURITY_FORM_IS_ENCODE_PWD_KEY = "isEncodePwd";

    private String isEncodePwdParameter = SPRING_SECURITY_FORM_IS_ENCODE_PWD_KEY;

    private boolean postOnly = true;

    private JwtPasswordEncoder passwordEncoder;

    @Autowired
    private IacUserService iacUserService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private BaseUserMapper baseUserMapper;

    public JwtLoginFilter(AuthenticationManager authenticationManager, JwtPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        super.setAuthenticationManager(authenticationManager);
        super.setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        username = (username != null) ? username : "";
        username = username.trim();

        String password = obtainPassword(request);
        password = (password != null) ? password : "";

        String loginType = obtainLoginType(request);
        if(StrUtil.isBlank(loginType)){
            throw new AuthenticationServiceException("登陆类型为空");
        }

        String isEncodePwdStr = obtainIsEncodePwd(request);
        if(StrUtil.isBlank(isEncodePwdStr) || isEncodePwdStr.equalsIgnoreCase("true")) {
            password = this.passwordEncoder.encode(password);
        }

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(username, password, loginType);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request, JwtAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    /**
     * 登录成功签发token返回json数据
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        // 获取登陆用户信息
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();

        // 生成token
        String access_token = JwtUtil.createAccessToken(loginUser);

        // 保存Token信息到Redis中
        JwtUtil.setTokenInfo(access_token, loginUser);

        // 返回json数据
        ApiResult apiResult = ApiResult.ok("登陆成功");

        //将wxid存储到数据库里
        String wxid = request.getHeader("wxid");
        if (StrUtil.isNotBlank(wxid)){
            BaseUser user = baseUserMapper.selectById(loginUser.getUserId());
            user.setWxId(wxid);
            baseUserMapper.update(user,new QueryWrapper<BaseUser>().eq("id", loginUser.getUserId()));
        }

        // 返回json数据
        ResponseUtil.write(response, apiResult.put("access_token", access_token).toString());
    }

    /**
     * 登录失败处理
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        JsonResult result;
        if (e instanceof UsernameNotFoundException) {
            result = JsonResult.failure("账号不存在");
        } else if (e instanceof BadCredentialsException) {
            result = JsonResult.failure("账号或密码错误");
        } else if (e instanceof LockedException) {
            result = JsonResult.failure("账号被锁定");
        } else {
            result = JsonResult.failure(e.getMessage());
        }
        ResponseUtil.write(response, result.toString());
    }

    @Nullable
    private String obtainLoginType(HttpServletRequest request) {
        return request.getParameter(this.loginTypeParameter);
    }

    private String obtainIsEncodePwd(HttpServletRequest request) {
        return request.getParameter(this.isEncodePwdParameter);
    }
}
