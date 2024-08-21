package com.example.demo.model.repository;

import com.example.demo.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String username);
//    Optional<User> findByUserName(String username);

    boolean existsUserByUserName(String username);

}
