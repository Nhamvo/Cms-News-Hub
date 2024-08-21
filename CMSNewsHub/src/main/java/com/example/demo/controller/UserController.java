package com.example.demo.controller;

import com.example.demo.controller.request.UserRequest;
import com.example.demo.model.entity.user.User;
import com.example.demo.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/public/hello")
    public Mono<ResponseEntity<String>> publicHello() {
        return Mono.just(ResponseEntity.ok("Đây là trang người dùng, không yêu cầu đăng nhập"));
    }

    @GetMapping("/admin/user")
    public Mono<ResponseEntity<List<User>>> getListUsers(){
        return Mono.just(ResponseEntity.ok(userService.getAllUsers()));
    }


    @PostMapping("/admin/user")
    public Mono<ResponseEntity<User>> addUsers(@RequestBody UserRequest userRequest){
        return Mono.just(ResponseEntity.ok(userService.createUsers(userRequest)));

    }

    @PutMapping("/admin/user/{id}")
    public Mono<User> updateUsers(@PathVariable Long id ,@RequestBody UserRequest userRequest){
        return Mono.just(userService.updateUsers(id,userRequest));

    }


    @DeleteMapping("/admin/user/{id}")
    public Mono<ResponseEntity<?>> deleteUsersByid(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();

        try {
            User userCheck = userService.findUserByid(id);
            if(userCheck==null){
                response.put("message", "User does not exist");
                return Mono.just(ResponseEntity.ok(response));
            }
            userService.deleteUsers(id);
            response.put("message", "User deleted successfully");
            return Mono.just(ResponseEntity.ok(response));
        } catch (UsernameNotFoundException e) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("article not found"));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete article"));
        }
    }


}
