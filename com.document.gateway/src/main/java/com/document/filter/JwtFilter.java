package com.document.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Base64;

public class JwtFilter implements GlobalFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());
    public static final String AUTH_HEADER = "Authorization";
    private final String PREFIX_BEARER = "Bearer ";

    private final String secretKey;
    private Key key;


    public JwtFilter(@Value("${jwt.secret-key}") String tokenSecret) {
        byte[] bytes = Base64.getDecoder().decode(tokenSecret);
        key = Keys.hmacShaKeyFor(bytes);
        secretKey = tokenSecret;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // users로 가는 요청은 막지 않는다.
        if (exchange.getRequest().getURI().getPath().startsWith("/users")) {
            return chain.filter(exchange);
        }

        // 토큰 추출
        String token = extractToken(exchange);

        // token 없거나 비정상적이면 401에러
        if (token == null || !validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Username을 갖고 온다
        Claims claimInfoFromToken = getClaimInfoFromToken(token);
        String username = claimInfoFromToken.getSubject();

        // 헤더에 유저 정보를 넣어줌으로써 다른 클라이언트에서 사용가능하게끔 한다.
        ServerHttpRequest requestWithUser = exchange.getRequest().mutate().header("userInfo", username).build();
        ServerWebExchange exchangeWithUser = exchange.mutate().request(requestWithUser).build();

        return chain.filter(exchangeWithUser);
    }

    private String extractToken(ServerWebExchange exchange) {
        String authHeader =  exchange.getRequest().getHeaders().getFirst(AUTH_HEADER);

        if (authHeader != null && authHeader.startsWith(PREFIX_BEARER)) {
            return authHeader.substring(7);
        }

        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            // 추가 검증 로직 가능
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private Claims getClaimInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
