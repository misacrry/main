package cn.ruishan.common.security;

import cn.ruishan.main.service.LoginUserDetailsService;
import cn.ruishan.main.service.impl.LoginUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置
 * Created by wangfan on 2020-03-23 18:04
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(JWTConfig.antMatchers).permitAll()
                .anyRequest().authenticated()
                // 禁用session（使用Token认证）
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().frameOptions().disable()
                // 开启跨域
                .and().cors()
                // 禁用跨站请求伪造防护
                .and().csrf().disable();
        http.exceptionHandling().accessDeniedHandler(jwtExceptionHandler()).authenticationEntryPoint(jwtExceptionHandler());
        http.logout().logoutUrl("/logout").logoutSuccessHandler(jwtLogoutSuccessHandler());
        http.addFilterBefore(jwtLoginByIdFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtApiLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider()).userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationProvider authenticationProvider (){
        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(passwordEncoder(), userDetailsService());
        return authenticationProvider;
    }

    @Bean
    public JwtPasswordEncoder passwordEncoder() {
        return new JwtPasswordEncoder();
    }

    @Override
    @Bean
    public LoginUserDetailsService userDetailsService() {
        return new LoginUserServiceImpl();
    }

    @Bean
    public JwtLoginByIdFilter jwtLoginByIdFilter() throws Exception {
        return new JwtLoginByIdFilter(authenticationManagerBean(), passwordEncoder());
    }

    @Bean
    public JwtApiLoginFilter jwtApiLoginFilter() throws Exception {
        return new JwtApiLoginFilter(authenticationManagerBean(), passwordEncoder());
    }

    @Bean
    public JwtLoginFilter jwtLoginFilter() throws Exception {
        return new JwtLoginFilter(authenticationManagerBean(), passwordEncoder());
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(userDetailsService());
    }

    @Bean
    public JwtLogoutSuccessHandler jwtLogoutSuccessHandler() {
        return new JwtLogoutSuccessHandler();
    }

    @Bean
    public JwtExceptionHandler jwtExceptionHandler() {
        return new JwtExceptionHandler();
    }

}
