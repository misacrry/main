package cn.ruishan.common.config;

import cn.ruishan.common.security.JWTConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @desc: 属性配置
 * @author: longgang.lei
 * @create: 2021-04-10 21:23
 **/
@Configuration
public class PropertiesConfig {

    @Bean
    public JWTConfig jwtConfig() {
        return new JWTConfig();
    }
}
