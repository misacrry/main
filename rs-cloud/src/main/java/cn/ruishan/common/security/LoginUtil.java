package cn.ruishan.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUtil {

	/**
     * 获取当前登录的user
     */
    public static LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != "anonymousUser") {
            Object object = authentication.getPrincipal();
            if (object != null) {
                return (LoginUser) object;
            }
        }
        return null;
    }

    /**
     * 获取当前登录的userId
     */
    public static Integer getLoginUserId() {
        LoginUser loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getUserId();
    }

    /**
     * 获取当前所属项目ID
     */
    public static Integer getLoginProjId() {
        LoginUser loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getProjId();
    }

    /**
     * 获取当前登陆用户名
     * @return
     */
    public static String getLoginName() {
        LoginUser loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getUsername();
    }

    /**
     * @desc: 设置当前登陆信息中
     * @author: longgang.lei
     * @time: 2021-08-09 10:07
     */
    public static void setLoginInfo(LoginUser loginUser) {

        JwtAuthenticationToken authentication = new JwtAuthenticationToken(
                loginUser, null, loginUser.getLoginType(), loginUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
