package com.example.cloud.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.cloud.security.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token生成工具类
 */
@Slf4j
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "username";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    public static final Long JWT_TTL = 60 * 60 * 24 * 1000L;

    // 令牌自定义标识
    @Value("${jwt.token.header}")
    private String header;

    // 令牌秘钥
    @Value("${jwt.token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟），也可将token的过期时间交给redis管理
    @Value("${jwt.token.expireTime}")
    private Long expiration;

    /**
     * 生成accessToken
     */
    public String createAccessToken(String loginId, Integer userId, Date nowTime) {
        Date expiresAt = new Date(nowTime.getTime() + 1000 * 60 );
        String token = null;
        try {
            token = JWT
                    .create()
                    .withSubject(String.valueOf(userId))
                    .withIssuedAt(nowTime)
                    .withExpiresAt(expiresAt)
                    .withClaim("loginId", loginId)
                    .sign(Algorithm.HMAC256(secret));
        } catch (Exception e) {
            throw new RuntimeException("accessToken生成异常");
        }
        return token;
    }

    /**
     * 生成refreshToken
     */
    public String createRefreshToken (String loginId, Date loginTime, Integer userId, Date nowTime) {
        Date expiresAt = new Date(nowTime.getTime() + JWT_TTL );
        String token = null;
        try {
            token = JWT
                    .create()
                    .withSubject(String.valueOf(userId))
                    .withIssuedAt(nowTime)
                    .withExpiresAt(expiresAt)
                    .withClaim("loginId", loginId)
                    .withClaim("loginTime", loginTime)
                    .sign(Algorithm.HMAC256(secret));
        } catch (Exception e) {
            throw new RuntimeException("生成refreshToken异常");
        }
        return token;
    }

    /**
     * 解析token内容
     */
    public DecodedJWT parseFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.decode(token);

            // 验证JWT的签名
            algorithm.verify(decodedJWT);
            return decodedJWT;
        } catch (Exception e) {
            throw new RuntimeException("token解析异常");
        }
    }



    /**
     * 根据负责生成JWT的token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpiration())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

//    private Key privateKey;
//    @PostConstruct
//    public void init() throws Exception {
//        // 示例：生成和存储密钥
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
//        keyPairGenerator.initialize(521); // ES512 需要521位
//        KeyPair keyPair = keyPairGenerator.generateKeyPair();
//        privateKey = keyPair.getPrivate();
//    }
//
//    private String generateToken(Map<String, Object> claims) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setExpiration(generateExpiration())
//                .signWith(SignatureAlgorithm.ES512, privateKey)
//                .compact();
//    }

    /**
     * 从token中获取JWT的负载
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败：{}", token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间，返回Date
     */
    private Date generateExpiration() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取登录名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否还有效
     *
     * @param token        客户端传入的token
     * @param userDetails  从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    public boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        // expiredDate < new Date() 返回true, 反之false
        System.out.println("是否过期：  "+expiredDate.before(new Date()));
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
//        Claims claims = getClaimsFromToken(token);
//        return claims.getExpiration();
        DecodedJWT decodedJWT = parseFromToken(token);
        return decodedJWT.getExpiresAt();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(SecurityUser securityUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, securityUser.getUsername());
        claims.put(CLAIM_KEY_USER_ID, securityUser.getUser().getId());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 获取请求token
     */
    public String getToken(HttpServletRequest request) {
        return request.getHeader(header);
    }

//    public String refreshToken (long userId) {
//
//    }


}
