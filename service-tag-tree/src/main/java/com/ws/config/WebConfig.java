package com.ws.config;

import com.ws.common.interceptor.RespResultInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 *
 * @author wangsen_a
 * @date 2023/12/13
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 注册拦截器
     * 将拦截器注册到spring中
     *
     * @param registry 注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //ResponseResultVO拦截器
        registry.addInterceptor(new RespResultInterceptor());
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
