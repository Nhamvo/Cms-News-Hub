package com.example.demo.model.service.impl;

import com.example.demo.controller.request.SavedArticleRequest;
import com.example.demo.model.dto.SavedArticleDTO;
import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.SavedArticle;
import com.example.demo.model.entity.user.User;
import com.example.demo.model.repository.ArticleRepository;
import com.example.demo.model.repository.SavedArticleRepository;
import com.example.demo.model.repository.UserRepository;
import com.example.demo.model.service.ArticleService;
import com.example.demo.model.service.SavedArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SavedArticleServiceImpl implements SavedArticleService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SavedArticleRepository savedArticleRepository;

    @Override
    public List<SavedArticleDTO> getAllSavedArticlesByUserId(Long id) {
        return savedArticleRepository.findAllSavedArticleByUserId(id);
    }

    @Override
    public SavedArticle savedArticle(SavedArticleRequest savedArticleRequest) {
        SavedArticle savedArticle = modelMapper.map(savedArticleRequest, SavedArticle.class);
        Article article = articleRepository.findById(savedArticleRequest.getArticle()).orElse(null);
        User user = userRepository.findById(savedArticleRequest.getUser()).orElse(null);
        savedArticle.setArticle(article);
        savedArticle.setUser(user);
        return savedArticleRepository.save(savedArticle);
    }

}
