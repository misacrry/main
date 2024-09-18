package cn.ruishan.main.service;

import cn.ruishan.common.security.LoginUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface LoginUserDetailsService extends UserDetailsService {

    LoginUser loadUserByUsername(String username, String loginType, String wxId) throws UsernameNotFoundException;
}
