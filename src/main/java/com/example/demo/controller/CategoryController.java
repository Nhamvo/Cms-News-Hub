package com.example.demo.controller;


import com.example.demo.model.dto.ArticleRequest;
import com.example.demo.model.dto.SeoContentRequest;
import com.example.demo.model.entity.Article;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity<List<Category>>> getAllCategories (){
        return  Mono.just(ResponseEntity.ok(categoryService.getListCategories()));
    }
    @PostMapping("/category")
    public Mono<ResponseEntity<Category>> createCategories (@RequestBody Category category){
        return Mono.just(ResponseEntity.ok(categoryService.createCategories(category)));

    }

    @PutMapping("/category/{id}")
    public Mono<Category> updateCategories(@PathVariable Long id , @RequestBody Category category){
         return Mono.just(categoryService.updateCategories(id,category));

    }

    @DeleteMapping("/category/{id}")
    public Mono<ResponseEntity<?>> deleteCategoriesByid(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();

        try {
            Category category = categoryService.findCategoriesById(id);
            if(category==null){
                response.put("message", "Categories does not exist");
                return Mono.just(ResponseEntity.ok(response));
            }
            categoryService.deleteCategories(id);
            response.put("message", "Categories deleted successfully");
            return Mono.just(ResponseEntity.ok(response));
        } catch (UsernameNotFoundException e) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("article not found"));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete article"));
        }
    }
}
