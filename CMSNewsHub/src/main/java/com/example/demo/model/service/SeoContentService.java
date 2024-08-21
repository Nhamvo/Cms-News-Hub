package com.example.demo.model.service;

import com.example.demo.controller.request.SeoContentRequest;
import com.example.demo.model.entity.SeoContent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeoContentService {
    List<SeoContent> getListSeoContent();
    SeoContent createSeoContents(SeoContentRequest category);
    void deleteSeoContents(Long id);
    SeoContent updateSeoContents(Long id ,SeoContentRequest category);
    SeoContent findSeoContentsById (Long id);

}
