package com.example.demo.model.service.impl;

import com.example.demo.controller.request.UserNotificationRequest;
import com.example.demo.model.entity.UserNotificationPreferences;
import com.example.demo.model.entity.user.User;
import com.example.demo.model.repository.UserNotificationPreferencesRepository;
import com.example.demo.model.repository.UserRepository;
import com.example.demo.model.service.UserNotificationPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserNotificationPreferencesServiceImpl implements
        UserNotificationPreferencesService {

    @Autowired
    private UserNotificationPreferencesRepository rs;

    @Autowired
    private UserRepository userRepo;

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    @Override
    public String saveUserNotificationPreferences(UserNotificationRequest request) {

        Long userId = request.getUserId();
        User user = userRepo.findById(userId).orElse(null);
        if (isValidEmail(user.getEmail())) {
            Set<Long> categories = request.getCategoryId();
            UserNotificationPreferences preferences = new UserNotificationPreferences();
            for (Long categoryId : categories) {
                try {
                    preferences.setUserId(userId);
                    preferences.setMailUser(user.getEmail());
                    preferences.setCategoryId(categoryId);
                    preferences.setStatus(1); // bật thông báo
                    rs.save(preferences);
                } catch (DataIntegrityViolationException e) {
                    // xử lý khi danh mục đã được đăng ký
                    System.out.printf("Danh mục %d đã được đăng ký%n", categoryId);
                    e.printStackTrace();
                    return "Danh mục " + categoryId + " đã được đăng ký";
                } catch (Exception e) {
                    System.out.println("Lỗi không xác định");
                    e.printStackTrace();
                    return "Đã xảy ra lỗi";
                }
            }
            return "Đăng ký bật thông báo thành công";
        }
        return "Kiểm tra lại thông tin email";


    }
}
