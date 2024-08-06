package com.example.demo.controller;


import com.example.demo.model.dto.SeoContentRequest;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.SeoContent;
import com.example.demo.model.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    public CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories (){
        return ResponseEntity.ok(categoryService.getListCategories());
    }
    @PostMapping("/category")
    public ResponseEntity<Category> createCategories (@RequestBody Category category){
        Category categoryResuilt = categoryService.createCategories(category);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/admin/category" + categoryResuilt.getCategoryId()));
        return new ResponseEntity<>(categoryResuilt,httpHeaders, HttpStatus.CREATED);
    }


    @PutMapping("/category/{id}")
    public ResponseEntity<Category> updateCategories(@PathVariable Long id , @RequestBody Category category){
        Category categoryResuilt = categoryService.updateCategories(id,category);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/seo-content/" + categoryResuilt.getCategoryId()));
        return new ResponseEntity<>(categoryResuilt,httpHeaders,HttpStatus.CREATED);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategoriesByid(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();

        try {
            Category category = categoryService.findCategoriesById(id);
            if(category==null){
                response.put("message", "Categories does not exist");
                return ResponseEntity.ok(response);
            }
            categoryService.deleteCategories(id);
            response.put("message", "Categories deleted successfully");
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete user");
        }
    }
}
