package com.example.cloud.config;


import com.example.cloud.model.User;
import com.example.cloud.security.SecurityUser;
import com.example.cloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 将Security拦截的用户名密码改为数据库中已有的用户名密码,Security自己校验密码，
 * 默认使用PasswordEncoder,格式为{id}password（id代表加密方式），
 * 一般不采用此方式，SpringSecurity提供了BcryptPasswordEncoder,只需将此注入到spring容器中。
 * SpringSecurity就会使用它进行替换校验
 */

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        // 查询用户信息
        if (phone == null || "".equals(phone)) {
            throw new  RuntimeException("用户名不能为空");
        }
        User user = userService.selectOneByPhone(phone);
        System.out.println(user);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 查询授权信息
        return new SecurityUser(user);
    }
}
