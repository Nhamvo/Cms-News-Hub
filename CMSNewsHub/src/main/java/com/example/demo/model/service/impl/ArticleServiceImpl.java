package com.example.demo.model.service.impl;


import com.example.demo.controller.request.ArticleRequest;
import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.user.User;
import com.example.demo.model.repository.ArticleRepository;
import com.example.demo.model.repository.CategoryRepository;
import com.example.demo.model.repository.UserRepository;
import com.example.demo.model.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRs;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;



    @Override
    public List<Article> getListArticle() {
        return articleRs.findAll();
    }

    @Override
    public Article createArticle(ArticleRequest articleRequest) {
        Article article = modelMapper.map(articleRequest, Article.class);
        Set<Category> categories = categoryRepository.findAllById(articleRequest.getCategories()).
                stream().collect(Collectors.toSet());
        article.setCategories(categories);
        article.setView(1);

        User author = userRepository.findById(articleRequest.getAuthor()).orElseThrow(() ->
                new RuntimeException("Author not found"));
        article.setAuthor(author);
        return articleRs.save(article);
    }

    @Override
    public void deleteArticle(Long id) {
        articleRs.deleteById(id);
    }

    @Override
    public Article updateArticle(Long id, ArticleRequest articleRequest) {


        Article article = articleRs.findById(id).orElse(null);
        modelMapper.map(articleRequest,article);
        User author = userRepository.findById(articleRequest.getAuthor()).orElseThrow(() -> new RuntimeException("Author not found"));
        article.setAuthor(author);
        article.setView(1);
        article.setLastEditedBy(author);
        Set<Category> categories = categoryRepository.findAllById(articleRequest.getCategories()).stream().collect(Collectors.toSet());
        article.setCategories(categories);
        articleRs.save(article);
        return article;
    }

    @Override
    public Article findArticleByid(Long id) {
        return articleRs.findById(id).orElse(null);
    }

//    public User getCurrentUser() {
//        BasicFilter basicFilter = new BasicFilter();
//        Mono<String> monoUser = basicFilter.getCurrentUser();
//        System.out.printf("ffffffffff"+ monoUser.toString());
//        return monoUser.toFuture().join();
//    }
}
