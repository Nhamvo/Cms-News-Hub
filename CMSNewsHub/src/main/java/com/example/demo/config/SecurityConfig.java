
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {


        return http
                .authorizeExchange()
                .pathMatchers("/public/**").permitAll()
                .pathMatchers("/editor/**").hasAnyAuthority("ROLE_User", "ROLE_Admin")
                .pathMatchers("/admin/**").hasAuthority("ROLE_Admin")
                .anyExchange().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .authenticationSuccessHandler((webFilterExchange, authentication) -> {
                    ServerWebExchange exchange = webFilterExchange.getExchange();
                    Map<String, String> result = new HashMap<>();
                    result.put("message", "Login successful");
                    String roles = authentication.getAuthorities().stream()
                            .map(grantedAuthority -> grantedAuthority.getAuthority())
                            .collect(Collectors.joining(", "));
                    result.put("role", roles);
                    exchange.getResponse().setStatusCode(HttpStatus.OK);
                    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().
                            bufferFactory().wrap(result.toString().getBytes())));
                })
                .authenticationFailureHandler((webFilterExchange, exception) -> {
                    ServerWebExchange exchange = webFilterExchange.getExchange();
                    Map<String, String> result = new HashMap<>();
                    result.put("message", "Login failed");
                    exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().
                            bufferFactory().wrap(result.toString().getBytes())));
                })
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler((webFilterExchange, authentication) -> {
                    ServerWebExchange exchange = webFilterExchange.getExchange();
                    Map<String, String> result = new HashMap<>();
                    result.put("message", "Logout successful");
                    exchange.getResponse().setStatusCode(HttpStatus.OK);
                    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().
                            bufferFactory().wrap(result.toString().getBytes())));
                })
                .and()
                .build();
    }


}
