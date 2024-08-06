package com.example.demo.config;

import com.example.demo.model.entity.user.Role;
import com.example.demo.model.entity.user.User;
import com.example.demo.model.repository.RoleRepository;
import com.example.demo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConfig {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            Role adminRole = Role.builder().name("Admin").build();
            Role userRole = Role.builder().name("User").build();
            if (!roleRepository.existsRoleByName("Admin")){
                adminRole = roleRepository.save(adminRole);
            }
            if (!roleRepository.existsRoleByName("User")){
                userRole = roleRepository.save(userRole);
            }

            User user = User.builder()
                    .userName("test")
                    .passWord(passwordEncoder.encode("123456"))
                    .email("ok")
                    .roles(Set.of(adminRole))
                    .build();
            User user1= User.builder()
                    .userName("test2")
                    .email("ok2")
                    .passWord(passwordEncoder.encode("123456"))
                    .roles(Set.of(userRole))
                    .build();

            if (!userRepository.existsUserByUserName(user.getUserName())){
                userRepository.save(user);
            }
            if (!userRepository.existsUserByUserName(user1.getUserName())){
                userRepository.save(user1);
            }
        };
    }
}
