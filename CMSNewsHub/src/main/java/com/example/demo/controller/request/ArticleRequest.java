package com.example.demo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleRequest {
    private String title;
    private String content;
    private Long author;
//    private Long lastEditedById;
    private String summary;
    private String tag;
    private String slug;
    private String featuredImage;
    private Integer status;
    private List<Long> categories;
}