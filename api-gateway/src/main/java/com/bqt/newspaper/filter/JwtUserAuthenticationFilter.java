package com.bqt.newspaper.filter;

import com.bqt.newspaper.jwt.JwtHelper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtUserAuthenticationFilter implements GatewayFilter {
    private final JwtHelper jwtHelper;

    // ServerWebExchange to perform operations on request and response
    // GatewayFilterChain to forward the request if necessary
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        if (authMissing(request)) return onError(exchange);

        String token = request.getHeaders().getOrEmpty("Authorization").get(0);

        if (token != null && token.startsWith("Bearer ")) token = token.substring(7);

        try {
            jwtHelper.validateJwtToken(token);

            Claims claims = jwtHelper.getClaims(token);

            List<String> roles = (List<String>) claims.get("roles");

            if(roles.get(0).equals("USER")){
                return chain.filter(exchange);
            } else {
                return onError(exchange);
            }

        } catch (Exception e) {
            return onError(exchange);
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
