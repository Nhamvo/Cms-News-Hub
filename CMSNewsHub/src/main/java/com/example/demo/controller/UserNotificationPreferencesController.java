package com.example.demo.controller;


import com.example.demo.controller.request.UserNotificationRequest;
import com.example.demo.model.entity.UserNotificationPreferences;
import com.example.demo.model.entity.user.User;
import com.example.demo.model.service.UserNotificationPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/user/notifications")
public class UserNotificationPreferencesController {

    @Autowired
    private UserNotificationPreferencesService userNotificationPreferencesService;

    @PostMapping("/subscribe")
    public Mono<ResponseEntity<?>> subscribeNotification
            (@RequestBody UserNotificationRequest userNotificationRequest) {

        return Mono.just(ResponseEntity.ok(userNotificationPreferencesService.
                saveUserNotificationPreferences(userNotificationRequest)));

    }

}
