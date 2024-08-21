package com.example.demo.model.service;


import com.example.demo.controller.request.ArticleRequest;
import com.example.demo.model.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    List<Article> getListArticle() ;
    Article createArticle(ArticleRequest article);
    void deleteArticle(Long id);
    Article updateArticle(Long id , ArticleRequest articleRequest);
    Article findArticleByid(Long id);

}
