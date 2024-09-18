package cn.ruishan.common.security;

import cn.hutool.core.util.StrUtil;
import cn.ruishan.common.utils.ResponseUtil;
import cn.ruishan.common.web.JsonResult;
import cn.ruishan.main.service.LoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @desc: 请求过滤器,处理携带token的请求
 * @author: longgang.lei
 * @time: 2021-04-05 13:09
 */
public class JwtRequestFilter extends OncePerRequestFilter {

    private final LoginUserDetailsService userDetailsService;

    public JwtRequestFilter(LoginUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 取出token
        String access_token = JwtUtil.getAccessToken(request);

        // token不为空，并且指定前缀
        if (StrUtil.isNotBlank(access_token)) {

            // 是否在黑名单中
            if (JwtUtil.isBlackList(access_token)) {
                ResponseUtil.write(response, JsonResult.failure(505,"Token已失效").toString());
                return;
            }

            // 是否存在于Redis中
            if (JwtUtil.hasToken(access_token)) {

                String expiration = JwtUtil.getExpirationByToken(access_token);
                LoginUser loginUser = JwtUtil.parseAccessToken(access_token);

                // 判断是否过期
                if (JwtUtil.isExpiration(expiration)) {
                    // 加入黑名单
                    JwtUtil.addBlackList(access_token);

                    // 是否在刷新期内
                    String validTime = JwtUtil.getRefreshTimeByToken(access_token);
                    if (JwtUtil.isValid(validTime)) {
                        // 刷新Token，重新存入请求头
                        String newToken = JwtUtil.refreshAccessToken(access_token, userDetailsService);

                        // 删除旧的Token，并保存新的Token
                        JwtUtil.deleteRedisToken(access_token);
                        JwtUtil.setTokenInfo(newToken, loginUser);

                        ResponseUtil.write(response, JsonResult.ok(209,"刷新token").put("access_token", newToken).toString());
                        return;
                    }
                    // Token已过期且超过刷新时间，不予刷新
                    else {
                        // 删除token
                        JwtUtil.deleteRedisToken(access_token);
                        ResponseUtil.write(response, JsonResult.failure(505,"Token已过期，已超过刷新有效期").toString());
                        return;
                    }
                }

                if (loginUser != null) {
                    JwtAuthenticationToken authentication = new JwtAuthenticationToken(
                            loginUser, null, loginUser.getLoginType(), loginUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }

}
