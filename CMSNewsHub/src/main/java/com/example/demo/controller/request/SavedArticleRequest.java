package com.example.demo.controller.request;


import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SavedArticleRequest {
    private Long user;
    private Long article;
}
