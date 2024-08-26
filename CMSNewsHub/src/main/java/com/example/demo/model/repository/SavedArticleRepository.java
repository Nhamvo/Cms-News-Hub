package com.example.demo.model.repository;


import com.example.demo.model.dto.SavedArticleDTO;
import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.SavedArticle;
import com.example.demo.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedArticleRepository extends JpaRepository<SavedArticle , Long> {

    @Query(value = "select new com.example.demo.model.dto.SavedArticleDTO(s.id,s.user.id," +
            "s.article.id,s.article.title,s.article.summary,s.article.featuredImage,s.article.slug) " +
            "from SavedArticle s where s.user.id=:id")
    List<SavedArticleDTO> findAllSavedArticleByUserId(Long id);

    boolean existsByUserAndArticle(User user, Article article);

}
