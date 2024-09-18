package cn.ruishan.common.security;

import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

/**
 * 授权
 * @author longgang.lei
 * @date 2019年9月11日
 */
@Accessors(chain = true)
@Getter
@Setter
public class OauthGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private String authority;

	@Override
	public String getAuthority() {
		return this.authority;
	}

}
