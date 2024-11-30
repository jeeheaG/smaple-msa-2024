package com.spring_cloud.eureka.client.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class LocalJwtAuthenticationFilter implements GlobalFilter {

    @Value("${service.jwt.secret-key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 회원가입 요청에 대해서는 토큰 검증 안함
        String path = exchange.getRequest().getURI().getPath();
        if (path.equals("/auth/sign-in")) {
            return chain.filter(exchange);
        }
        
        String token = extractToken(exchange);

        // 토큰값이 없거나 유효하지 않을 때
        if (token == null || !validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 토큰에 문제가 없으면 다음 필터 계속 실행
        return chain.filter(exchange);
    }

    /**
     * 헤더에서 토큰값 추출하는 메서드
     * @param exchange
     * @return
     */
    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * 토큰값 검증 메서드
     * @param token
     * @return
     */
    private boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build().parseSignedClaims(token);
            log.info("#####payload :: " + claimsJws.getPayload().toString());

            // 추가적인 검증 로직 (예: 토큰 만료 여부 확인 등)을 여기에 추가할 수 있습니다.
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
