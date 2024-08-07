package com.example.demo.model.service.impl;


import com.example.demo.model.dto.SeoContentRequest;
import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.SeoContent;
import com.example.demo.model.repository.ArticleRepository;
import com.example.demo.model.repository.SeoContentRepository;
import com.example.demo.model.service.SeoContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeoContentServiceImpl implements SeoContentService {
    @Autowired
    private SeoContentRepository seoContentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<SeoContent> getListSeoContent() {
        return seoContentRepository.findAll();
    }

    @Override
    public SeoContent createSeoContents(SeoContentRequest seoContentRequest) {
        Article article = articleRepository.findById(seoContentRequest.getArticle()).orElse(null);
        SeoContent seoContent =new SeoContent();
        seoContent.setMetaKeywords(seoContentRequest.getMetaKeywords());
        seoContent.setMetaTitle(seoContent.getMetaTitle());
        seoContent.setArticle(article);
        seoContent.setMetaDescription(seoContentRequest.getMetaDescription());
        return seoContentRepository.save(seoContent);
    }

    @Override
    public void deleteSeoContents(Long id) {
        seoContentRepository.deleteById(id);
    }

    @Override
    public SeoContent updateSeoContents(Long id, SeoContentRequest seoContentRequest) {
        SeoContent seoContentResuilt = seoContentRepository.findById(id).orElse(null);
        seoContentResuilt.setMetaDescription(seoContentRequest.getMetaDescription());
        seoContentResuilt.setMetaKeywords(seoContentRequest.getMetaKeywords());
        seoContentResuilt.setMetaTitle(seoContentRequest.getMetaTitle());
        Article article = articleRepository.findById(seoContentRequest.getArticle()).orElse(null);
        seoContentResuilt.setArticle(article);
        return seoContentRepository.save(seoContentResuilt);
    }

    @Override
    public SeoContent findSeoContentsById(Long id) {
        return seoContentRepository.findById(id).orElse(null);
    }


}
