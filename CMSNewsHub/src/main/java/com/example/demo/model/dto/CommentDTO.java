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
    private Long idUser;
    private Long idArticle;
    private String comment;
    private Integer rating;
    private LocalDateTime commentDate;
    private String username; // Tên của User
    private String articleTitle; // Tiêu đề của bài viết

}