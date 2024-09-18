package cn.ruishan.common.security;

import cn.ruishan.common.utils.ResponseUtil;
import cn.ruishan.common.web.JsonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @desc: 没有权限异常处理
 * @author: longgang.lei
 * @time: 2021-04-05 12:58
 */
public class JwtExceptionHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

    /**
     * 已登录访问被拒异常处理
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException, ServletException {
        doHandler(request, response, e);
    }

    /**
     * 未登录访问被拒异常处理
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        doHandler(request, response, e);
    }

    private void doHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        JsonResult result;
        if (e instanceof AccessDeniedException) {
            result = JsonResult.failure(403, "没有访问权限");
        } else if (e instanceof InsufficientAuthenticationException) {
            result = JsonResult.failure(401, "未登录");
        } else if (e instanceof AccountExpiredException) {
            result = JsonResult.failure(401, "登录已过期");
        } else {
            result = JsonResult.failure(401, "未登录或登录已过期").put("error", e.toString());
        }
        ResponseUtil.write(response, result.toString());
    }

}
