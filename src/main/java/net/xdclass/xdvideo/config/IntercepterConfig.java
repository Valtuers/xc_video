package net.xdclass.xdvideo.config;

import net.xdclass.xdvideo.intercepter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 */
@Configuration
public class IntercepterConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/user/api/v1/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
