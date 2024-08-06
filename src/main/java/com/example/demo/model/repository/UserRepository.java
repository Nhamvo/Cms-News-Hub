package com.example.demo.model.repository;

import com.example.demo.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String username);
    boolean existsUserByUserName(String username);

}
