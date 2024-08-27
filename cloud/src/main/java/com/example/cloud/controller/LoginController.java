package com.example.cloud.controller;

import com.example.cloud.common.ApiResult;
import com.example.cloud.config.JwtTokenUtil;
import com.example.cloud.config.RedisUtil;
import com.example.cloud.model.User;
import com.example.cloud.security.SecurityUser;
import com.example.cloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/sys")
public class LoginController {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public ApiResult login(@RequestBody User user, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getPhone(), user.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成token
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Integer userId = securityUser.getUser().getId();
        String jwtToken = jwtTokenUtil.generateToken(securityUser);
        Date nowTime = new Date();
        String loginId = UUID.randomUUID().toString().replace("-", "");
        System.out.println("userID   "+userId);
        String accessToken = jwtTokenUtil.createAccessToken(loginId, userId, nowTime);
        String refreshToken = jwtTokenUtil.createRefreshToken(loginId, nowTime, userId, nowTime);
        Map<String, Object> result = new HashMap<>(6);
        result.put("accessToken", accessToken);
        result.put("expiresIn", 20);
        result.put("refreshToken", refreshToken);
        result.put("refreshExpiresIn", 24 * 60 * 60);
        result.put("tokenType", "Bearer");
        // redis保存用户信息
        securityUser.setRefreshToken(refreshToken);
        boolean resultRedis = redisUtil.set("login" + userId, securityUser);
        if (!resultRedis) {
            System.out.println("redis连接失败，登录失败");
            throw new RuntimeException("redis连接失败，登录失败");
        }
        // 返回 token 与用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2);
        authInfo.put("token", result);
        authInfo.put("username", securityUser.getUser().getPhone());
        authInfo.put("userId", securityUser.getUser().getId());
        // 把accessToken放在请求头
        Cookie cookie = new Cookie("token",accessToken);
        cookie.setDomain("localhost");
        cookie.setSecure(false);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ApiResult.success(authInfo);
    }

    @PostMapping("/register")
    @ResponseBody
    public ApiResult userRegister(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode("123456"));
        if (!Objects.isNull(userService.selectOneByPhone(user.getPhone()))) {
            throw new RuntimeException("用户名已存在");
        }
        userService.addOne(user);
        return ApiResult.success("注册成功");
    }

    @GetMapping(value = "/logout")
    public ApiResult logout() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authenticationToken.getPrincipal();
        redisUtil.del("login" + securityUser.getUser().getId());
        return ApiResult.success("登出成功");
    }

    @GetMapping("/refresh")
    public ApiResult refresh(@RequestParam String accessToken) {
        if (accessToken == null || accessToken == "") {
            throw new RuntimeException("accessToken为空");
        }
        String refreshToken;
        return null;
    }


}
