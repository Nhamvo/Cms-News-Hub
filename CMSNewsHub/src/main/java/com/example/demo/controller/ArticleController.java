package com.example.demo.controller;


import com.example.demo.config.ReactiveUserDetailsImpl;
import com.example.demo.controller.request.ArticleRequest;
import com.example.demo.model.entity.Article;
import com.example.demo.model.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
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
 import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/editor")
public class ArticleController {
    @Autowired
    public ArticleService articleService;
    ReactiveUserDetailsImpl reactiveUserDetails = new ReactiveUserDetailsImpl();



    @GetMapping("/article-manager")
    public Mono<ResponseEntity<List<Article>>> getAllArticles() {

        return Mono.just(ResponseEntity.ok(articleService.getListArticle()));

    }

    @GetMapping("/article-manager/{id}")
    public Mono<ResponseEntity<Article>> getArticlesById(@PathVariable Long id) {
        return Mono.just(ResponseEntity.ok(articleService.findArticleByid(id)));
    }

    @PostMapping("/article-manager")
    public Mono<ResponseEntity<Article>> createArticle(@RequestBody ArticleRequest articleRequest) {
        return Mono.just(ResponseEntity.ok(articleService.createArticle(articleRequest)));

    }

    @PutMapping("/article-manager/{id}")
    public Mono<Article> updateArticles(@PathVariable Long id,
                                        @RequestBody ArticleRequest articleRequest) {
        return Mono.just(articleService.updateArticle(id, articleRequest));

    }


    @DeleteMapping("/article-manager/{id}")
    public Mono<ResponseEntity<?>> deleteCategoriesByid(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();

        try {
            Article article = articleService.findArticleByid(id);
            if (article == null) {
                response.put("message", "article does not exist");
                return Mono.just(ResponseEntity.ok(response));
            }
            articleService.deleteArticle(id);
            response.put("message", "article deleted successfully");
             return Mono.just(ResponseEntity.ok(response));
        } catch (UsernameNotFoundException e) {
             return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("article not found"));
        } catch (Exception e) {
             return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete article"));
        }
    }


}
