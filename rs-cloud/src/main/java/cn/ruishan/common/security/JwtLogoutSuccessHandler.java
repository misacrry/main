package cn.ruishan.common.security;

import cn.ruishan.common.utils.ResponseUtil;
import cn.ruishan.common.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @desc: 退出登录处理
 * @author: longgang.lei
 * @time: 2021-04-05 13:05
 */
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        // 添加到黑名单
        String token = JwtUtil.getAccessToken(request);
        JwtUtil.addBlackList(token);
        JwtUtil.deleteRedisToken(token);

        // 清空
        SecurityContextHolder.clearContext();

        ResponseUtil.write(response, JsonResult.ok("登出成功").toString());
    }

}
