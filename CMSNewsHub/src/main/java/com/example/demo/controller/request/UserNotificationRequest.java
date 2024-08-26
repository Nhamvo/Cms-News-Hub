package com.example.demo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserNotificationRequest {
    private Long userId;
    private Set<Long> categoryId;


}
