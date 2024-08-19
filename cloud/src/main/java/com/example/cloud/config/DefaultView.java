package com.example.cloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DefaultView implements WebMvcConfigurer  {

//    @Resource
//    private JwtInterceptor jwtInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor);
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        // 默认访问index.html
        registry.addViewController("/").setViewName("forward:login.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
