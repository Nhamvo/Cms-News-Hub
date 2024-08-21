package com.example.demo.controller;


import com.example.demo.controller.request.CommentRequest;
import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/user")
public class CommentController {

    @Autowired
    CommentService commentService ;

    @PostMapping("/create-comment")
    public Mono<ResponseEntity<Comment>> createComment(@RequestBody CommentRequest comment){
        return Mono.just(ResponseEntity.ok(commentService.createComments(comment)));
    }

    @GetMapping("/get-comment-by-article/{id}")
    public Mono<ResponseEntity<List<CommentDTO>>> getCommentsByArticle(@PathVariable Long id){
        return Mono.just(ResponseEntity.ok(commentService.getCommentsByArticleId(id)));
    }

    @GetMapping("/get-comment-by-user/{id}")
    public Mono<ResponseEntity<List<CommentDTO>>> getCommentsByUser(@PathVariable Long id){
        return Mono.just(ResponseEntity.ok(commentService.getCommentsByUserId(id)));
    }

}
