package ynu.elm.filter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {
    
    private static final Set<String> ALLOWED_PATHS = Set.of(
        "/UserController/getUserByIdByPass",
        "/UserController/saveUser",
        "/BusinessController/getBusinessByIdByPass",
        "/BusinessController/saveBusiness",
        "/AdminController/getAdminByIdByPass",
        "/AdminController/saveAdmin",
        "/DeliveryDriverController/getDriverByIdByPass",
        "/DeliveryDriverController/saveDriver"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Processing request path: " + exchange.getRequest().getURI().getPath());
        System.out.println("Request method: " + exchange.getRequest().getMethod());
        String path = exchange.getRequest().getURI().getPath();
        if (ALLOWED_PATHS.stream().anyMatch(path::contains)) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        token = token.substring(7).trim().replaceAll("^\"|\"$", "");

        try {
            System.out.println("Token: " + token);
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor("wang-yong-shuo-w-y-s-12345678901".getBytes()))
                    .build()
                    .parseSignedClaims(token);
            return chain.filter(exchange);
        } catch (JwtException e) {
            System.out.println("JWT Exception: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } catch (Exception e) {
            System.out.println("General Exception: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}