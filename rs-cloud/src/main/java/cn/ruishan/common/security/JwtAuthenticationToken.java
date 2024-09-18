package cn.ruishan.common.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 110L;
    private final Object principal;
    private Object credentials;
    private String loginType;
    private String wxId;

    /**
     * This constructor can be safely used by any code that wishes to create a
     * <code>UsernamePasswordAuthenticationToken</code>, as the {@link
     * #isAuthenticated()} will return <code>false</code>.
     *
     */
    public JwtAuthenticationToken(Object principal, Object credentials, String loginType) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.loginType = loginType;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(Object principal, Object credentials, String loginType, String wxId) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.loginType = loginType;
        this.wxId = wxId;
        this.setAuthenticated(false);
    }
    /**
     * This constructor should only be used by <code>AuthenticationManager</code> or <code>AuthenticationProvider</code>
     * implementations that are satisfied with producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
     * token token.
     *
     * @param principal
     * @param credentials
     * @param authorities
     */
    public JwtAuthenticationToken(Object principal, Object credentials, String loginType, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.loginType = loginType;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getLoginType() {
        return this.loginType;
    }

    public String getWxId() {
        return this.wxId;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if(isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
