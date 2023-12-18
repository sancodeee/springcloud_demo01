package com.ws.config;

import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关全局过滤器
 *
 * @author wangsen_a
 * @date 2023/12/18
 */
// @Order(-1)
@Accessors(chain = true)
@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter {

    private final String X_ACCESS_TOKEN = "X-Access-Token";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        // 请求头中的token
        String headerToken = headers.getFirst(X_ACCESS_TOKEN);
        // 获取请求参数
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        // 获取token验证
        String token = queryParams.getFirst(X_ACCESS_TOKEN);
        log.info("X-Access-Token值：{}", token);
        log.info("Header中X-Access-Token值：{}", token);
        if ("token".equals(token) || "token".equals(headerToken)) {
            // 放行：继续执行过滤器链
            return chain.filter(exchange);
        }
        // 禁止访问
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        log.error("请求参数中token验证不通过");
        // 返回不同过验证的请求
        return exchange.getResponse().setComplete();
    }


}
