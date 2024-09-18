package cn.ruishan.common.security;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.ruishan.common.utils.ResponseUtil;
import cn.ruishan.common.web.JsonResult;
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
 * @desc: 登录过滤器，通过关联关系登陆
 * @author: longgang.lei
 * @time: 2021-04-05 12:57
 */
public class JwtLoginByIdFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * loginId
     */
    public static final String SPRING_SECURITY_FORM_LOGIN_ID_KEY = "loginId";

    private String loginIdParameter = SPRING_SECURITY_FORM_LOGIN_ID_KEY;

    /**
     * loginType
     */
    public static final String SPRING_SECURITY_FORM_LOGIN_TYPE_KEY = "loginType";

    private String loginTypeParameter = SPRING_SECURITY_FORM_LOGIN_TYPE_KEY;

    private boolean postOnly = true;

    private JwtPasswordEncoder passwordEncoder;


    public JwtLoginByIdFilter(AuthenticationManager authenticationManager, JwtPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        super.setAuthenticationManager(authenticationManager);
        super.setFilterProcessesUrl("/loginById");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String loginIdStr = obtainLoginId(request);
        String loginType = obtainLoginType(request);
        String username = null;
        String password = null;
        if(StrUtil.isNotBlank(loginIdStr) && StrUtil.isNotBlank(loginType)) {
            if(!NumberUtil.isInteger(loginIdStr)) {
                throw new AuthenticationServiceException("login id is not number");
            }
        } else {
            throw new AuthenticationServiceException("login id and type cannot be empty");
        }

        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            throw new AuthenticationServiceException("账号或密码为空或不存在");
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
        ResponseUtil.write(response, JsonResult.ok("登录成功").put("access_token", access_token).toString());
    }

    /**
     * 登录失败处理
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        String username = request.getParameter("username");
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

    private String obtainLoginId(HttpServletRequest request) {
        return request.getParameter(this.loginIdParameter);
    }

    private String obtainLoginType(HttpServletRequest request) {
        return request.getParameter(this.loginTypeParameter);
    }
}
