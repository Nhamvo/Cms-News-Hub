package com.example.demo.controller;


import com.example.demo.controller.request.CommentRequest;
import com.example.demo.controller.request.SavedArticleRequest;
import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.dto.SavedArticleDTO;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.SavedArticle;
import com.example.demo.model.service.CommentService;
import com.example.demo.model.service.SavedArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/user")

public class SavedArticleController {
    @Autowired
    SavedArticleService savedArticleService ;

    @PostMapping("/saved-article")
    public Mono<ResponseEntity<SavedArticle>> savedArticle(@RequestBody SavedArticleRequest savedArticleRequest){
        return Mono.just(ResponseEntity.ok(savedArticleService.savedArticle(savedArticleRequest)));
    }

    @GetMapping("/get-saved-article/{id}")
    public Mono<ResponseEntity<List<SavedArticleDTO>>> getSavedArticleByUser(@PathVariable Long id){
        return Mono.just(ResponseEntity.ok(savedArticleService.getAllSavedArticlesByUserId(id)));
    }

}
