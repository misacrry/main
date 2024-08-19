package com.example.cloud.security;

import com.alibaba.fastjson.JSONObject;

import com.example.cloud.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        //当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法发送403 Forbidden响应
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(JSONObject.toJSONString(ApiResult.error(HttpStatus.FORBIDDEN.value(),"鉴权失败")));
        response.getWriter().flush();
    }
}