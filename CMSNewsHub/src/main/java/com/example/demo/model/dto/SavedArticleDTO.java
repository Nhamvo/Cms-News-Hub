package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SavedArticleDTO {
    private Long id;
    private Long idUser;
    private Long articleId;
    private String articleTitle;
    private String articleSummary;
    private String articleFeaturedImage;
    private String articleSlug;
 }
