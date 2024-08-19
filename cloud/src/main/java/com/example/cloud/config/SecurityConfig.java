package com.example.cloud.config;

import com.example.cloud.security.JwtAuthenticationTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration  // 注册为Springboot的配置类
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 注入Jwt认证拦截器
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    /**
     * BCryptPasswordEncoder 将用户输入的密码转化为一个散列值，存入到数据库
     * 用户尝试登录时，输入的密码会再次散列，与数据库的散列值进行对比，以验证身份
     * 将BCryptPasswordEncoder 加密器注入 SpringSecurity 中，
     * SpringSecurity 的 DaoAuthenticaionProvider 会调用该加密器中的 match() 方法进行密码对比，密码对比不需要我们干涉
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入身份验证管理器，直接继承即可
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 禁用跨站请求伪造
                .csrf().disable()
                // 禁用session，使用token作为信息传递介质
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 把token校验过滤器加到过滤器链，添加在UsernamePasswordAuthenticationFilter之前是因为只要用户携带token,
                // 就不需要再去验证是否有用户名密码了 (而且我们不使用表单登入, UsernamePasswordAuthenticationFilter是无法解析Json的, 相当于它没用了)
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 认证请求的配置
                .authorizeRequests()
                // 登录界面放开
                .antMatchers("/static/**", "/layui/**", "/echarts/**", "/icon/**","/echa","/*.js", "/*.css","/*.html" , "/css/**", "/js/**").permitAll()
                // 将登入和注册的接口放开
                .antMatchers("/").anonymous()
                .antMatchers("/sys/login").anonymous()
                .antMatchers("/sys/register").anonymous()
                // 其他所以接口请求都需要经过认证
                .anyRequest().authenticated()


                .and()
                // 配置登录页面和处理
//                .formLogin()
//                .loginPage("/login1.html")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/sys/logout")
//                .logoutSuccessUrl("/login1.html")
//                .and()
                // 允许跨域请求
                .cors();
    }
}
