package com.spring_cloud.eureka.client.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomPreFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(CustomPreFilter.class.getName());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 요청 전 수행할 작업
        ServerHttpRequest request = exchange.getRequest();
        log.info("preFilter: request URI: " + request.getURI());

        // 다음 필터 호출
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // preFilter : 우선순위 최상위
    }
}
