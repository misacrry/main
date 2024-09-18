package cn.ruishan.common.security;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.ruishan.common.utils.RedisUtil;
import cn.ruishan.main.service.LoginUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @desc: Token工具类
 * @author: longgang.lei
 * @time: 2021-04-05 13:05
 */
@Component
public class JwtUtil {

    private static Log log = Log.get(JwtUtil.class);

    private static RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 时间格式化
     */
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取请求中的access_token
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String getAccessToken(HttpServletRequest request) {
        String access_token = request.getParameter(JWTConfig.tokenParam);
        if (StrUtil.isBlank(access_token)) {
            access_token = request.getHeader(JWTConfig.tokenHeader);
        }

        if(StrUtil.isNotBlank(access_token)){
            access_token = JwtUtil.delTokenPrefix(access_token);
        }

        return access_token;
    }

    /**
     * 创建Token
     *
     * @param loginUser 用户信息
     * @return
     */
    public static String createAccessToken(LoginUser loginUser) {
        String token = Jwts.builder()// 设置JWT
                .setId(loginUser.getUserId().toString()) // 用户Id
                .setSubject(loginUser.getLoginname()) // 主题
                .setIssuedAt(new Date()) // 签发时间
                .setIssuer("RS") // 签发者
                .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration)) // 过期时间
                .signWith(parseKey(JWTConfig.secret)) // 签名算法、密钥
                .claim("loginType", loginUser.getLoginType())
                .compact();
        return token;
    }

    /**
     * 刷新Token
     *
     * @param oldToken 过期但未超过刷新时间的Token
     * @return
     */
    public static String refreshAccessToken(String oldToken, LoginUserDetailsService userDetailsService) {
        String username = JwtUtil.getLoginNameByToken(oldToken);
        String loginType = JwtUtil.getLoginTypeByToken(oldToken);
        LoginUser loginUser = userDetailsService.loadUserByUsername(username, loginType, null);
        return createAccessToken(loginUser);
    }

    /**
     * 解析Token
     *
     * @param token Token信息
     * @return
     */
    public static LoginUser parseAccessToken(String token) {
        LoginUser loginUser = null;
        Claims claims;
        try {
            // 解析Token
            claims = Jwts.parserBuilder().setSigningKey(parseKey(JWTConfig.secret)).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.error("解析Token异常：" + e);
            claims = e.getClaims();
        }
        // 获取用户信息
        loginUser = new LoginUser();

        Map<Object, Object> map = redisUtil.hmget(token);
        BeanUtil.copyProperties(map, loginUser);

        loginUser.setUserId(Integer.parseInt(claims.getId()));
        loginUser.setLoginname(claims.getSubject());

        // 获取loginType
        String loginType = claims.get("loginType").toString();
        loginUser.setLoginType(loginType);
        return loginUser;
    }

    /**
     * 生成密钥Key
     *
     * @return Key
     */
    public static Key genKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    /**
     * 生成字符串(base64)形式的key
     *
     * @return String
     */
    public static String genKeyStr() {
        return Base64.getEncoder().encodeToString(genKey().getEncoded());
    }

    /**
     * 解析Key
     *
     * @param keyStr base64格式的key
     * @return Key
     */
    public static Key parseKey(String keyStr) {
        if (StrUtil.isNotBlank(keyStr)) {
            return Keys.hmacShaKeyFor(Base64.getDecoder().decode(keyStr));
        } else {
            throw new RuntimeException("key为空");
        }
    }

    /**
     * 保存Token信息到Redis中
     *
     * @param token    Token信息
     * @param loginUser 登陆用户实体
     */
    public static void setTokenInfo(String token, LoginUser loginUser) {
        // 删除其他登陆用户信息
        /*String prefix = StrUtil.subBefore(token, ".", false);
        Set<String> keys = redisUtil.getKeys();
        for (String key : keys) {
            if(key.contains(prefix)) {
                redisUtil.del(key);
            }
        }*/

        Long refreshTime = JWTConfig.refreshTime;
        LocalDateTime localDateTime = LocalDateTime.now();

        Map<String, Object> map = BeanUtil.beanToMap(loginUser);
        map.put("refreshTime", df.format(localDateTime.plus(JWTConfig.refreshTime, ChronoUnit.MILLIS)));
        map.put("expiration", df.format(localDateTime.plus(JWTConfig.expiration, ChronoUnit.MILLIS)));

        redisUtil.hmset(token, map, refreshTime);
    }

    /**
     * 将Token放到黑名单中
     *
     * @param token Token信息
     */
    public static void addBlackList(String token) {
        redisUtil.hset("blackList", token, df.format(LocalDateTime.now()));
    }

    /**
     * Redis中删除Token
     *
     * @param token Token信息
     */
    public static void deleteRedisToken(String token) {
        redisUtil.del(token);
    }

    /**
     * 判断当前Token是否在黑名单中
     *
     * @param token Token信息
     */
    public static boolean isBlackList(String token) {
        return redisUtil.hasKey("blackList", token);
    }

    /**
     * 是否过期
     *
     * @param expiration 过期时间，字符串
     * @return 过期返回True，未过期返回false
     */
    public static boolean isExpiration(String expiration) {
        LocalDateTime expirationTime = LocalDateTime.parse(expiration, df);
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.compareTo(expirationTime) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 是否有效
     *
     * @param refreshTime 刷新时间，字符串
     * @return 有效返回True，无效返回false
     */
    public static boolean isValid(String refreshTime) {
        LocalDateTime validTime = LocalDateTime.parse(refreshTime, df);
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.compareTo(validTime) > 0) {
            return false;
        }
        return true;
    }

    /**
     * 检查Redis中是否存在Token
     *
     * @param token Token信息
     * @return
     */
    public static boolean hasToken(String token) {
        return redisUtil.hasKey(token);
    }

    /**
     * 从Redis中获取过期时间
     *
     * @param token Token信息
     * @return 过期时间，字符串
     */
    public static String getExpirationByToken(String token) {
        return redisUtil.hget(token, "expiration").toString();
    }

    /**
     * 从Redis中获取刷新时间
     *
     * @param token Token信息
     * @return 刷新时间，字符串
     */
    public static String getRefreshTimeByToken(String token) {
        return redisUtil.hget(token, "refreshTime").toString();
    }

    /**
     * 从Redis中获取登陆名
     *
     * @param token Token信息
     * @return
     */
    public static String getLoginNameByToken(String token) {
        return redisUtil.hget(token, "loginname").toString();
    }

    /**
     * 从Redis中获取登陆类型
     *
     * @param token Token信息
     * @return
     */
    public static String getLoginTypeByToken(String token) {
        return redisUtil.hget(token, "loginType").toString();
    }

    /**
     * 从Redis中获取登陆用户名称
     *
     * @param token Token信息
     * @return
     */
    public static String getUserNameByToken(String token) {
        return redisUtil.hget(token, "username").toString();
    }
    /**
     * 从Redis中获取登陆用户所属项目
     *
     * @param token Token信息
     * @return
     */
    public static Integer getProjIdByToken(String token) {
        return (Integer) redisUtil.hget(token, "projId");
    }
    /**
     * 从Redis中获取登陆用户权限列表
     *
     * @param token Token信息
     * @return
     */
    public static List<OauthGrantedAuthority> getAuthoritiesByToken(String token) {
        return (List<OauthGrantedAuthority>) redisUtil.hget(token, "authorities");
    }
    /**
     * 从Redis中获取登陆用户资源
     *
     * @param token Token信息
     * @return
     */
    public static List<Resource> getResourcesByToken(String token) {
        return (List<Resource>) redisUtil.hget(token, "resources");
    }

    /**
     * 去除token前缀
     *
     * @param token Token信息
     * @return
     */
    public static String delTokenPrefix(String token) {
        if(StrUtil.isNotBlank(token)) {
            if(token.length() > JWTConfig.tokenPrefix.length()) {
                return token.substring(JWTConfig.tokenPrefix.length());
            } else {
                throw new RuntimeException("token格式不正确");
            }
        } else {
            throw new RuntimeException("token为空");
        }
    }
}
