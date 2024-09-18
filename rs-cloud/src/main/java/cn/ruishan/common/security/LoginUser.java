package cn.ruishan.common.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 用户实体绑定spring security
 * @author longgang.lei
 * @date 2019年9月9日
 */
@Accessors(chain = true)
@Getter
@Setter
public class LoginUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Integer userId;

	private String loginname;

	private String username;

	private String loginType;

	private Integer corpId;

	private String corpName;

	private Integer projId;

	private String projName;

	private Integer substationId;


	@JsonIgnore
	private String password;

	@JsonIgnore
	private boolean status;

	private List<OauthGrantedAuthority> authorities;

	private List<Resource> resources;

	/**
	 * 返回授予用户的权限。不能返回空值。
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.authorities;
	}

	/**
	 * 返回用于对用户进行身份验证的密码。
	 */
	@Override
	public String getPassword() {

		return this.password;
	}

	/**
	 * 返回用于验证用户的用户名。不能返回空值。
	 */
	@Override
	public String getUsername() {

		return this.username;
	}

	/**
	 * 指示用户的帐户是否已过期。过期的帐户无法进行身份验证。
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	/**
	 * 指示用户是锁定还是解锁。锁定的用户无法进行身份验证。
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	/**
	 * 指示用户的凭据（密码）是否已过期。过期的凭据阻止身份验证。
	 */
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	/**
	 * 指示用户是启用还是禁用。禁用的用户无法进行身份验证。
	 */
	@JsonIgnore
	@Override
	public boolean isEnabled() {

		return true;
	}
}
