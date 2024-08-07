package com.example.demo.model.service;

import com.example.demo.model.dto.UserRequest;
import com.example.demo.model.entity.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();
    User createUsers(UserRequest user);
    void deleteUsers(Long id);
    User findUserByid (Long id);
    User updateUsers(Long id,UserRequest user);
//    Mono<UserDetails> findByUsername(String username) ;
}
