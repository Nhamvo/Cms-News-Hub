package com.example.demo.model.entity;

import com.example.demo.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "saved_articles")
public class SavedArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false,unique = true)
    private Article article;

    @Column(name = "saved_date", nullable = false)
    private LocalDateTime savedDate;

    @PrePersist
    protected void onCreate() {
        this.savedDate = LocalDateTime.now();
    }


}