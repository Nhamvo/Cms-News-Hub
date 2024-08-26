package com.example.demo.model.repository;

import com.example.demo.model.dto.SavedArticleDTO;
import com.example.demo.model.entity.UserNotificationPreferences;
import com.example.demo.model.entity.UserNotificationPreferencesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface UserNotificationPreferencesRepository extends JpaRepository<UserNotificationPreferences,
        UserNotificationPreferencesId> {

    @Query(value = "select s.mailUser from UserNotificationPreferences s where s.categoryId=:id")
    Set<String> getUserMailForNotification(Long id);
}
