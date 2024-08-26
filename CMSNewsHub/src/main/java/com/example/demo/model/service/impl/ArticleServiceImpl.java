package com.example.demo.model.service.impl;


import com.example.demo.config.EmailService;
import com.example.demo.controller.request.ArticleRequest;
import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.user.User;
import com.example.demo.model.repository.ArticleRepository;
import com.example.demo.model.repository.CategoryRepository;
import com.example.demo.model.repository.UserNotificationPreferencesRepository;
import com.example.demo.model.repository.UserRepository;
import com.example.demo.model.service.ArticleService;
import com.example.demo.model.specification.ArticleSpecification;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
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
    @Autowired
    UserNotificationPreferencesRepository userNotificationPreferencesRs;


    @Autowired
    EmailService emailService;

    @Override
    public List<Article> getListArticle() {
        return articleRs.findAll();
    }

    @Override
    public Article createArticle(ArticleRequest articleRequest) throws MessagingException {
        Article article = modelMapper.map(articleRequest, Article.class);
        Set<Category> categories = categoryRepository.findAllById(articleRequest.getCategories()).
                stream().collect(Collectors.toSet());
        article.setCategories(categories);
        article.setView(1);
        User author = userRepository.findById(articleRequest.getAuthor()).orElseThrow(() ->
                new RuntimeException("Author not found"));
        article.setAuthor(author);
        sendMailNotificationToUser(articleRequest);
        return articleRs.save(article);

    }


    void sendMailNotificationToUser(ArticleRequest articleRequest) throws MessagingException {
        Set<String> listUserMail = new HashSet<>();
        for (Long categoryId : articleRequest.getCategories()) {
            listUserMail =  userNotificationPreferencesRs.
                    getUserMailForNotification(categoryId);
        }
        for (String userMail : listUserMail) {
            System.out.printf("list user mail  \n" + userMail);
            emailService.sendEmailWithAttachment(userMail, articleRequest.getTitle());
        }
    }

    @Override
    public void deleteArticle(Long id) {
        articleRs.deleteById(id);
    }

    @Override
    public Article updateArticle(Long id, ArticleRequest articleRequest) {
        Article article = articleRs.findById(id).orElse(null);
        modelMapper.map(articleRequest, article);
        User author = userRepository.findById(articleRequest.getAuthor()).orElseThrow(() ->
                new RuntimeException("Author not found"));
        article.setAuthor(author);
        article.setView(1);
        article.setLastEditedBy(author);
        Set<Category> categories = categoryRepository.findAllById(articleRequest.getCategories()).
                stream().collect(Collectors.toSet());
        article.setCategories(categories);
        article.setLastModifiedDate(LocalDateTime.now());
        articleRs.save(article);
        return article;
    }

    @Override
    public Article findArticleByid(Long id) {
        return articleRs.findById(id).orElse(null);
    }

    @Override
    public List<Article> hasRecentArticles(Long categoryId, Integer dayAgo) {
        Specification<Article> spec = Specification.where(ArticleSpecification.hasCategory(categoryId));
        // Thực hiện truy vấn với Specification
        if (dayAgo != null) {
            spec = spec.and(ArticleSpecification.hasRecentArticles(dayAgo));
        }
        return articleRs.findAll(spec);
    }

//    public User getCurrentUser() {
//        BasicFilter basicFilter = new BasicFilter();
//        Mono<String> monoUser = basicFilter.getCurrentUser();
//        System.out.printf("ffffffffff"+ monoUser.toString());
//        return monoUser.toFuture().join();
//    }
}
