package cn.ruishan.common.redis;

import cn.hutool.core.text.StrBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.time.Duration;

/**
 * @desc: 缓存配置类
 *  用于注解的缓存配置，如 @Cacheable 的序列化配置
 *  说明：
 *  我们 redisUtils 序列化方式采用 json序列化
 *  @Cacheable 默认序列化方式为 二进制的
 *  两个不能混用，为了解决这个问题，这里设置 @Cacheable 默认序列化方式为 json
 *
 *  通过 @Cacheable、@CacheEvict 注解实现 操作，需要开启缓存 @EnableCaching
 *
 *  @Cacheable 注解是先根据 key 去查询 Redis 中是否有这个 key，如果有则直接返回
 *  如果没有则执行方法体，最后将方法返回内容添加到 Redis 中，key 为上面那个key
 *
 *  @CachePut 的逻辑是：执行方法体 - 将结果缓存起来，用于更新数据
 *
 *  @CacheEvict 注解是先执行方法体，然后根据 key 去 Redis 中删除
 *
 * @author: longgang.lei
 * @time: 2021-04-11 11:01
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 设置 redis 数据默认过期时间
     * 设置@cacheable 序列化方式
     *
     * @return
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)).entryTtl(Duration.ofDays(30));
        return configuration;
    }

    @Value("${jwt.expiration: 0}")
    private int expireTime;

    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * @desc: 设置自定义key{ClassName + methodName + params}
     * 查询
     * @author: longgang.lei
     * @time: 2021-04-11 11:22
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StrBuilder sb = StrBuilder.create();
            sb.append(target.getClass().getName());
            sb.append(",Method:");
            sb.append(method.getName());
            sb.append(",Params[");
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].toString());
                if (i != (params.length - 1)) {
                    sb.append(",");
                }
            }
            sb.append("]");
            log.debug("Data Caching Redis Key : {}", sb.toString());
            return sb.toString();
        };
    }

    /**
     * @desc: 自定义keyGenerator，Key生成器
     * 修改
     * @author: longgang.lei
     * @time: 2021-04-11 11:22
     */
    @Bean
    public KeyGenerator updateByIdkeyGenerator() {
        return (target, method, params) -> {
            StrBuilder sb = StrBuilder.create();
            sb.append(target.getClass().getName());
            sb.append(",Method:");
            sb.append("getById");
            sb.append(",Params[");
            try {
                Field id = params[0].getClass().getDeclaredField("id");
                id.setAccessible(true);
                sb.append(id.get(params[0]).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            sb.append("]");
            log.debug("Data Caching Redis Key : {}", sb.toString());
            return sb.toString();
        };
    }

    /**
     * @desc: 自定义keyGenerator，Key生成器
     * 删除
     * @author: longgang.lei
     * @time: 2021-04-11 11:22
     */
    @Bean
    public KeyGenerator deleteByIdkeyGenerator() {
        return (target, method, params) -> {
            StrBuilder sb = StrBuilder.create();
            sb.append(target.getClass().getName());
            sb.append(",Method:");
            sb.append("getById");
            sb.append(",Params[");
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].toString());
                if (i != (params.length - 1)) {
                    sb.append(",");
                }
            }
            sb.append("]");
            log.debug("Data Caching Redis Key : {}", sb.toString());
            return sb.toString();
        };
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存过期时间
        if (expireTime > 0) {
            log.info("Redis 缓存过期时间 : {}", expireTime);
            //设置缓存有效期 秒
            redisCacheConfiguration.entryTtl(Duration.ofSeconds(expireTime));
        } else {
            log.info("Redis 未设置缓存过期时间");
        }
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * @desc: 创建RedisTemplate
     * @author: longgang.lei
     * @time: 2021-04-11 11:55
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        // key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        // value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        // Hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
