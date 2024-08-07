package com.example.demo.model.service;

import com.example.demo.model.dto.ArticleRequest;
import com.example.demo.model.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

//    @Mapping(target = "categories", ignore = true) // Chúng ta sẽ ánh xạ danh mục riêng
//    Article articleRequestToArticle(ArticleRequest articleRequest);

}
