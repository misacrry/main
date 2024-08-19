package com.example.cloud.security;

import com.example.cloud.common.BusinessException;
import com.example.cloud.config.JwtTokenUtil;
import com.example.cloud.config.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * JWT 登录授权过滤器
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    // redis工具类
    @Resource
    private RedisUtil redisUtil;

    // JWT工具类
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    @Qualifier("handlerExceptionResolver")   // 将handlerExceptionResolver的bean注入到resolver变量中
    private HandlerExceptionResolver resolver;


    /**
     * 从请求中获取 JWT 令牌，并根据令牌获取用户信息，最后将用户信息封装到 Authentication 中
     * 方便后续校验(只会执行一次)
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        // 判断token是否带有文本
        if (!StringUtils.hasText(token)) {
            // token为空的话，就不管它，让SpringSecurity中的其他过滤器处理请求，请求放行
            filterChain.doFilter(request, response);
            return;
        }
        // token不为空时，解析token
        String userId = null;
        try {
            Claims claims = jwtTokenUtil.getClaimsFromToken(token);
            // 解析出userId
            if (!Objects.isNull(claims)) {
                Object userIdObject = claims.get("userId");
                userId = userIdObject.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            throw new RuntimeException("token非法");
        }
        // 使用userId从Redis缓存中获取用户信息
        String redisKey = "login" + userId;
        SecurityUser securityUser = (SecurityUser)redisUtil.get(redisKey);
        if (Objects.isNull(securityUser)) {
            throw new BusinessException("用户未登录");
        }
        // 将用户安全信息存入SecurityContextHolder，在之后SpringSecurity的过滤器就不会拦截
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}
