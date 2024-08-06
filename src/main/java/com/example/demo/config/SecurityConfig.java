package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    DataSource dataSource;
    @Autowired
    BasicFilter basicFilter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Cấu hình quy tắc phân quyền
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                // Các URL bắt đầu với /api/public/ không yêu cầu xác thực
                                .requestMatchers("/api/public/**", "/api/login").permitAll()

                                .requestMatchers("/editor/**").hasAnyAuthority("User","Admin")
                                .requestMatchers("/**").hasAuthority("Admin")

                                .anyRequest().authenticated()


                )



                // Cấu hình đăng nhập
                .formLogin(formLogin ->
                        formLogin
                                // URL xử lý đăng nhập
                                .loginProcessingUrl("/api/login")
                                .successHandler((request, response, authentication) -> {
                                    Map<String, String> result = new HashMap<>();
                                    result.put("JSESSIONID",((WebAuthenticationDetails) authentication.getDetails()).getSessionId());
                                    result.put("message", "Login successful");
                                    // Lấy danh sách các quyền của người dùng và nối lại thành một chuỗi
                                    String roles = authentication.getAuthorities().stream()
                                            .map(grantedAuthority -> grantedAuthority.getAuthority())
                                            .collect(Collectors.joining(", "));
                                    result.put("role",roles);
                                    // Cấu hình response
                                    response.setStatus(HttpServletResponse.SC_OK);
                                    response.setContentType("application/json");
                                    response.setCharacterEncoding("UTF-8");
                                    // Ghi JSON vào response
                                    response.getWriter().write(objectMapper.writeValueAsString(result));
                                })
                                .failureHandler((request, response, exception) -> {
                                    Map<String, String> result = new HashMap<>();
                                    result.put("message", "Login failed");
                                    // Cấu hình response
                                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                    response.setContentType("application/json");
                                    response.setCharacterEncoding("UTF-8");
                                    // Ghi JSON vào response
                                    response.getWriter().write(objectMapper.writeValueAsString(result));
                                })
                                // Cho phép tất cả truy cập đăng nhập
                                .permitAll()
                )
                // Cấu hình đăng xuất
                .logout(logout ->
                        logout
                                // URL xử lý đăng xuất
                                .logoutUrl("/api/logout")
                                // Cho phép tất cả truy cập đăng xuất
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                )

                .addFilterAfter(basicFilter, UsernamePasswordAuthenticationFilter.class)
                // Cấu hình CSRF
                .csrf(AbstractHttpConfigurer::disable);  // Tắt CSRF cho các API
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsServiceImpl userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public JdbcTokenRepositoryImpl persistentTokenRepository() {
        JdbcTokenRepositoryImpl memory = new JdbcTokenRepositoryImpl();
        memory.setDataSource(dataSource);
        return memory;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

}