package com.example.demo.model.service;

import com.example.demo.controller.request.UserNotificationRequest;
import com.example.demo.model.entity.UserNotificationPreferences;
import com.example.demo.model.repository.UserNotificationPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface UserNotificationPreferencesService {
    String saveUserNotificationPreferences(UserNotificationRequest userNotificationRequest);


}
