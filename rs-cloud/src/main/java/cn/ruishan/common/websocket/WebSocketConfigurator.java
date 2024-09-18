package cn.ruishan.common.websocket;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author RS-LLG
 */
@ConditionalOnWebApplication
@Configuration
public class WebSocketConfigurator {

    @Bean
    public CustomSpringConfigurator customSpringConfigurator() {
        // This is just to get context
        return new CustomSpringConfigurator();
    }
}
