package com.example.demo.model.service.impl;

import com.example.demo.config.SecurityConfig;
import com.example.demo.model.dto.UserRequest;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.user.Role;
import com.example.demo.model.entity.user.User;
import com.example.demo.model.repository.RoleRepository;
import com.example.demo.model.repository.UserRepository;
import com.example.demo.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public User createUsers(UserRequest userRequest) {
        User newUser = new User();
        newUser.setUserName(userRequest.getUserName());
        newUser.setEmail(userRequest.getUserName());
        String encodedPassword = passwordEncoder.encode(userRequest.getPassWord());

        newUser.setPassWord(encodedPassword);
        Set<Role> roles = roleRepository.findAllById(userRequest.getRoles()).stream().collect(Collectors.toSet());
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUsers(Long id) {
    userRepository.deleteById(id);

    }

    @Override
    public User findUserByid(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public User updateUsers(Long id, UserRequest user) {
        User userResuilt = userRepository.findById(id).orElse(null);
        userResuilt.setUserName(user.getUserName());
        userResuilt.setEmail(user.getEmail());
        String encodedPassword = passwordEncoder.encode(user.getPassWord());
        userResuilt.setPassWord(encodedPassword);
        Set<Role> roles = roleRepository.findAllById(user.getRoles()).stream().collect(Collectors.toSet());
        userResuilt.setRoles(roles);
        return userRepository.save(userResuilt);
    }




}
