package com.example.demo.model.service;


import com.example.demo.model.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getListCategories();
    Category createCategories(Category category);
    void deleteCategories(Long id);
    Category updateCategories(Long id , Category category);
    Category findCategoriesById(Long id );

}
