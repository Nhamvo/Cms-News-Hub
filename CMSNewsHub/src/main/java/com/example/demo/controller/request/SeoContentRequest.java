package com.example.demo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeoContentRequest {
    private Long article;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
}
