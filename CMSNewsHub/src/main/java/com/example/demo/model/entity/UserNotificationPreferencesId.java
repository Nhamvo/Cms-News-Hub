package com.example.demo.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserNotificationPreferencesId implements Serializable {
    private Long userId;
    private Long categoryId;

}
