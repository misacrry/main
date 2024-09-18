package cn.ruishan.common.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ruishan.common.utils.HttpUtil;
import cn.ruishan.common.utils.ResponseUtil;
import cn.ruishan.common.web.ApiResult;
import cn.ruishan.main.entity.LoginType;
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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 登录过滤器
 * @author: longgang.lei
 * @time: 2021-04-05 12:57
 */
public class JwtApiLoginFilter extends UsernamePasswordAuthenticationFilter {

    public static final String SPRING_SECURITY_FORM_LOGIN_TYPE_KEY = "loginType";

    private String loginTypeParameter = SPRING_SECURITY_FORM_LOGIN_TYPE_KEY;

    private boolean postOnly = true;

    private JwtPasswordEncoder passwordEncoder;

    public JwtApiLoginFilter(AuthenticationManager authenticationManager, JwtPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        super.setAuthenticationManager(authenticationManager);
        super.setFilterProcessesUrl("/api/ems/auth");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (this.postOnly && !StrUtil.equalsIgnoreCase(request.getMethod(), "POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String body = HttpUtil.getBodyParams(request);
        JSONObject jsonObject = JSONUtil.parseObj(body);
        String serialNum = jsonObject.getStr("serialNum");
        String uuid = jsonObject.getStr("uuid");

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(serialNum, uuid, LoginType.IAC_DEV);
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

        String username = loginUser.getLoginname();
        String loginType = loginUser.getLoginType();

        // 工商业设备登录
        if (StrUtil.equalsIgnoreCase(loginType, LoginType.IAC_DEV)) {

        }
        else {

            apiResult = ApiResult.failure("用户类型错误");
        }

        Map<String, Object> token = new HashMap<String, Object>();
        token.put("access_token", access_token);
        token.put("token_type", JWTConfig.tokenPrefix);
        token.put("expires_in", JWTConfig.expiration);
        apiResult.put("token", token);

        ResponseUtil.write(response, apiResult.toString());
    }

    /**
     * 登录失败处理
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        String username = request.getParameter("username");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ApiResult result;
        if (e instanceof UsernameNotFoundException) {
            result = ApiResult.failure("账号不存在");
        } else if (e instanceof BadCredentialsException) {
            result = ApiResult.failure("账号或密码错误");
        } else if (e instanceof LockedException) {
            result = ApiResult.failure("账号被锁定");
        } else {
            result = ApiResult.failure(e.getMessage());
        }
        out.write(result.toString());
        out.flush();
    }

    @Nullable
    private String obtainLoginType(HttpServletRequest request) {
        return request.getParameter(this.loginTypeParameter);
    }
}
