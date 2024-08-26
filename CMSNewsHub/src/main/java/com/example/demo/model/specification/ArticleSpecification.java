package com.example.demo.model.specification;

import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.user.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ArticleSpecification {
    public static Specification<Article> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Article> hasCategory(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            Join<Article, Category> categories = root.join("categories");
            return categoryId == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(categories.get("categoryId"), categoryId);
        };
    }
    public static Specification<Article> hasRecentArticles(Integer daysAgo) {
        return (root, query, criteriaBuilder) -> {
            LocalDateTime dateTimeThreshold = LocalDateTime.now().minus(daysAgo, ChronoUnit.DAYS);
            return criteriaBuilder.greaterThanOrEqualTo(root.get("publishedAt"), dateTimeThreshold);
        };
    }




}
