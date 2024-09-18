package cn.ruishan.common.config;

import cn.ruishan.common.security.JWTConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfig {

    /**
     * 连接url
     */
    public static String url;

    /**
     * 用户名
     */
    public static String username;

    /**
     * 密码
     */
    public static String password;

    public void setUrl(String url) {
        DataSourceConfig.url = url;
    }

    public void setUsername(String username) {
        DataSourceConfig.username = username;
    }

    public void setPassword(String password) {
        DataSourceConfig.password = password;
    }

}

