package cn.ruishan.common.security;

import com.dtflys.forest.annotation.Get;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @desc: JWT配置基础类
 * @author: longgang.lei
 * @time: 2021-04-10 19:32
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {

	/**
	 * 密匙Key
	 */
	public static String secret;

	/**
	 * HeaderKey
	 */
	public static String tokenHeader;

	/**
	 * ParamKey
	 */
	public static String tokenParam;

	/**
	 * Token前缀
	 */
	public static String tokenPrefix;

	/**
	 * 过期时间
	 */
	public static Long expiration;

	/**
	 * 有效时间
	 */
	public static Long refreshTime;

	/**
	 * 配置白名单
	 */
	public static String[] antMatchers;

	public void setSecret(String secret) {
		JWTConfig.secret = secret;
	}

	public void setTokenHeader(String tokenHeader) {
		JWTConfig.tokenHeader = tokenHeader;
	}

	public void setTokenParam(String tokenParam) {
		JWTConfig.tokenParam = tokenParam;
	}

	public void setTokenPrefix(String tokenPrefix) {
		JWTConfig.tokenPrefix = tokenPrefix + " ";
	}

	/**
	 * 将过期时间单位换算成毫秒
	 *
	 * @param expiration 过期时间，单位秒
	 */
	public void setExpiration(Long expiration) {
		JWTConfig.expiration = expiration * 1000;
	}

	/**
	 * 将有效时间单位换算成毫秒
	 *
	 * @param refreshTime 有效时间，单位秒
	 */
	public void setRefreshTime(Long refreshTime) {
		JWTConfig.refreshTime = refreshTime * 24 * 60 * 60 * 1000;
	}

	public void setAntMatchers(String[] antMatchers) {
		JWTConfig.antMatchers = antMatchers;
	}

}
