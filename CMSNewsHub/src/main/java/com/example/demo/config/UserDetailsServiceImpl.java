package com.example.demo.config;

import com.example.demo.model.entity.user.Role;
import com.example.demo.model.entity.user.User;
import com.example.demo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user != null){
            return buildUserForAuthentication(user);
        }
        return null;
    }

    private UserDetails buildUserForAuthentication(User user) {
        try {
            String[] roleNames = user.getRoles().stream()
                    .map(Role::getName)  // Lấy tên của mỗi Role
                    .toArray(String[]::new);
            return org.springframework.security.core.userdetails.User.builder().username(String.valueOf(user.getUserName()))
                    .password(user.getPassWord())
                    .roles(roleNames)
                    .authorities(roleNames).build();
        } catch (Exception e) {
            return null;
        }
    }

}
