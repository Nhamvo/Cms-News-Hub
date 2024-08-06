package com.example.demo.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seo_content")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeoContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seoId ;

    @OneToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;


    @Column
    private String metaTitle;

    @Column
    private String metaDescription;

    @Column
    private String metaKeywords;

}
