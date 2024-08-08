package com.example.demo.config;

import com.example.demo.model.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveUserDetailsImpl implements ReactiveUserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        com.example.demo.model.entity.user.User users = userRepository.findByUserName(username);

        return Mono.fromCallable(() -> users)
                .map(user -> {
                    if (user == null) {
                        throw new UsernameNotFoundException("User not found");
                    }
                    String roles = user.getRoleNames();
                    return org.springframework.security.core.userdetails.User.withUsername(passwordEncoder().encode(user.getUserName()))
                            .password(user.getPassWord())
                            .roles(roles)
                            .build();
                });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
