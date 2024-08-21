package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDTO {
    private Long idComment;
    private String comment;
    private Integer rating;
    private LocalDateTime commentDate;
    private Long idUser;
    private String username; // Tên của User
    private Long idArticle;
    private String articleTitle; // Tiêu đề của bài viết

}