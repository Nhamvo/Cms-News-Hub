package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_notification_preferences", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "category_id"})
})
@IdClass(UserNotificationPreferencesId.class)

public class UserNotificationPreferences {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "status", nullable = false)
    private Integer status; // 0: Tắt thông báo, 1: Bật thông báo

    @Column(name = "mail_user", nullable = false)
    private String mailUser;

}