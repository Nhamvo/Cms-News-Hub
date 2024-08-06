package com.example.demo.controller;


import com.example.demo.model.dto.ArticleRequest;
import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.Category;
import com.example.demo.model.service.ArticleService;
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
@RequestMapping("/editor")
public class ArticleController {
    @Autowired
    public ArticleService articleService;

    @GetMapping("/article-manager")
    public ResponseEntity<List<Article>> getAllArticles(){
        return ResponseEntity.ok(articleService.getListArticle()) ;
    }

    @PostMapping("/article-manager")
    public ResponseEntity<Article> createArticle(@RequestBody ArticleRequest articleRequest){
        Article createArticle = articleService.createArticle(articleRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/editor/article-manager/"+createArticle.getId()));
        return new ResponseEntity<>(createArticle,httpHeaders, HttpStatus.CREATED) ;

    }

    @PutMapping("/article-manager/{id}")
    public ResponseEntity<Article> updateArticles(@PathVariable Long id , @RequestBody ArticleRequest articleRequest){
        Article articleResuilt = articleService.updateArticle(id,articleRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/article-manager/" + articleResuilt.getId()));
        return new ResponseEntity<>(articleResuilt,httpHeaders,HttpStatus.CREATED);
    }

    @DeleteMapping("/article-manager/{id}")
    public ResponseEntity<?> deleteCategoriesByid(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();

        try {
            Article article = articleService.findArticleByid(id);
            if(article==null){
                response.put("message", "article does not exist");
                return ResponseEntity.ok(response);
            }
            articleService.deleteArticle(id);
            response.put("message", "article deleted successfully");
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete user");
        }
    }


}
