package com.example.demo.model.service.impl;

import com.example.demo.model.entity.Category;
import com.example.demo.model.repository.CategoryRepository;
import com.example.demo.model.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceimpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getListCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategories(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategories(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category updateCategories(Long id, Category category) {
        Category categoryNew = categoryRepository.findById(id).orElse(null);
        categoryNew.setDescription(category.getDescription());
        categoryNew.setName(category.getName());

        return categoryRepository.save(categoryNew);
    }

    @Override
    public Category findCategoriesById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
