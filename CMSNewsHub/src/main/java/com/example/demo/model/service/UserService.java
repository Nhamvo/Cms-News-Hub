package com.example.demo.model.service;

import com.example.demo.controller.request.UserRequest;
import com.example.demo.model.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();
    User createUsers(UserRequest user);
    void deleteUsers(Long id);
    User findUserByid (Long id);
    User updateUsers(Long id,UserRequest user);
}
