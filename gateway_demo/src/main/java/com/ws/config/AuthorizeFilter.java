package com.ws.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
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
@Order(-1)
@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter {

    private final String X_ACCESS_TOKEN = "X-Access-Token";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求参数
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        // 获取token验证
        String first = queryParams.getFirst(X_ACCESS_TOKEN);
        log.info("X-Access-Token值：{}", first);
        if ("token".equals(first)) {
            // 放行
            return chain.filter(exchange);
        }
        // 禁止访问
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        // 结束处理
        return exchange.getResponse().setComplete();
    }
}
