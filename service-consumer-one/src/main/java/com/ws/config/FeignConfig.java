package com.ws.config;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Configuration
public class FeignConfig {

    private final String X_ACCESS_TOKEN = "X-Access-Token";

    /**
     * Feign请求拦截器
     *
     * @return {@link RequestInterceptor}
     */
    @Bean
    public RequestInterceptor RequestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                // feiqn请求uri
                String requestURI = request.getRequestURI();
                log.info("feignRequestURI：{}", requestURI);
                // 获取请求中的token,将token信息放入header中
                String token = request.getHeader(X_ACCESS_TOKEN);
                if (token == null) {
                    token = request.getParameter("token");
                }
                log.info("feignRequestToken：{}", token);
                requestTemplate.header(X_ACCESS_TOKEN, token);
            }
        };
    }

}
