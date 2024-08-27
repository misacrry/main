package com.example.cloud.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.cloud.security.SecurityUser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = null;
        String refreshToken = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
        }
        if (accessToken == null || "".equals(accessToken)) {
            throw new RuntimeException("accessToken获取异常");
        }
        // token不为空时，解析token
        String userId = null;
        try {
            DecodedJWT decodedJWT = jwtTokenUtil.parseFromToken(accessToken);
            // 解析出userId
            if (!Objects.isNull(decodedJWT)) {
                Object userIdObject = decodedJWT.getSubject();
                userId = userIdObject.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            throw new RuntimeException("token非法");
        }
        SecurityUser securityUser = (SecurityUser) redisUtil.get("login" + userId);
        System.out.println(securityUser);
        refreshToken = securityUser.getRefreshToken();

        if (jwtTokenUtil.isTokenExpired(accessToken)) {
            System.out.println("accessToken已经过期");
        }

        // 两个token全部过期，重新登陆
        if (jwtTokenUtil.isTokenExpired(refreshToken) && jwtTokenUtil.isTokenExpired(accessToken)) {
            throw new Exception("两个token全部过期");
//            response.sendRedirect("/login.html");
//            return false;


//            throw new TokenExpiredException("token全部过期");
        } else if (jwtTokenUtil.isTokenExpired(accessToken) && !jwtTokenUtil.isTokenExpired(refreshToken)) {
            // accessToken过期，refreshToken没过期，刷新accessToken
            System.out.println("过期1：   "+jwtTokenUtil.isTokenExpired(accessToken));
            System.out.println("刷新token");
            DecodedJWT decodedJWT = jwtTokenUtil.parseFromToken(refreshToken);
            System.out.println("过期1时间 "+decodedJWT.getExpiresAt());
            Date now = new Date();
            System.out.println(now.getTime());
            accessToken = jwtTokenUtil.createAccessToken(userId, decodedJWT.getClaim("loginId").asInt(),now);
            System.out.println("过期2：   "+jwtTokenUtil.isTokenExpired(accessToken));
            System.out.println("过期2时间 "+jwtTokenUtil.parseFromToken(accessToken).getExpiresAt());

            // 更新cookie
            Cookie cookie = new Cookie("token",accessToken);
            cookie.setDomain("localhost");
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {

        }
        System.out.println(accessToken);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
