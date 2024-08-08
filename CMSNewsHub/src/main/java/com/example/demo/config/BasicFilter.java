package com.example.demo.config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BasicFilter implements WebFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
     public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(authentication -> {
                    if (authentication == null || !authentication.isAuthenticated()) {
                        // Người dùng chưa xác thực, trả về JSON thông báo lỗi
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                        exchange.getResponse().getHeaders().setContentLength(0);

                        Map<String, String> errorResponse = new HashMap<>();
                        errorResponse.put("error", "Unauthorized");
                        errorResponse.put("message", "You need to login to access this resource");

                        try {
                            return exchange.getResponse().writeWith(Mono.just(
                                    exchange.getResponse().bufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse))
                            ));
                        } catch (JsonProcessingException e) {
                            return Mono.error(e);
                        }
                    }
                    // Nếu đã xác thực, tiếp tục chuỗi filter
                    return chain.filter(exchange);
                });
    }


}
