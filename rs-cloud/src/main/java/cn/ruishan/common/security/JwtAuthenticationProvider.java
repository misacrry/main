package cn.ruishan.common.security;

import cn.ruishan.main.service.LoginUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private JwtPasswordEncoder passwordEncoder;

    private LoginUserDetailsService userDetailsService;

    public JwtAuthenticationProvider(JwtPasswordEncoder passwordEncoder, LoginUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        String username = (String) authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();
        String loginType = authenticationToken.getLoginType();
        String wxId = authenticationToken.getWxId();


        UserDetails loginUser = userDetailsService.loadUserByUsername(username, loginType, wxId);

        if (loginType.equals("IAC_USR_APP")){
            if (!passwordEncoder.encode(password).equals(loginUser.getPassword())){
                throw new BadCredentialsException("密码错误");
            }
        }else {
            if (!password.equals(loginUser.getPassword())) {
                throw new BadCredentialsException("密码错误");
            }
        }

        return new JwtAuthenticationToken(loginUser, password, loginType);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
