package com.example.demo.model.service;


import com.example.demo.controller.request.SavedArticleRequest;
import com.example.demo.model.dto.SavedArticleDTO;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.SavedArticle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SavedArticleService {


    List<SavedArticleDTO> getAllSavedArticlesByUserId(Long id);
    SavedArticle savedArticle(SavedArticleRequest savedArticleRequest);

}
