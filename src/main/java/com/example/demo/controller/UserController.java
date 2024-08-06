package com.example.demo.controller;

import com.example.demo.model.dto.UserRequest;
import com.example.demo.model.entity.user.User;
import com.example.demo.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/user")
    public ResponseEntity<List<User>> getListUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PostMapping("/user")
    public ResponseEntity<User> addUsers(@RequestBody UserRequest userRequest){
        User userResuilt = userService.createUsers(userRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/admin/user/add-users" + userResuilt.getId()));
        return new ResponseEntity<>(userResuilt,httpHeaders,HttpStatus.CREATED);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUsers(@PathVariable Long id ,@RequestBody UserRequest userRequest){
        User userResuilt = userService.updateUsers(id,userRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/admin/user/update-users" + userResuilt.getId()));
        return new ResponseEntity<>(userResuilt,httpHeaders,HttpStatus.CREATED);
    }


    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUsersByid(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();

        try {
            User userCheck = userService.findUserByid(id);
            if(userCheck==null){
                response.put("message", "User does not exist");
                return ResponseEntity.ok(response);
            }
            userService.deleteUsers(id);
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete user");
        }
    }


}
