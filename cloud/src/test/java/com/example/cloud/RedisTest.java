package com.example.cloud;

import com.example.cloud.config.RedisUtil;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

public class RedisTest {

    @Resource
     RedisUtil redisUtil;

    @Test
    void contextLoads35(){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        redisUtil.set("cloudTest","测试", 5);
    }

}
