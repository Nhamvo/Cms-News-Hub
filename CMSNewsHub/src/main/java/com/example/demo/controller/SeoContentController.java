package com.example.demo.controller;

import com.example.demo.controller.request.SeoContentRequest;
import com.example.demo.model.entity.SeoContent;
import com.example.demo.model.repository.SeoContentRepository;
import com.example.demo.model.service.SeoContentService;
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
@RequestMapping("/admin")
public class SeoContentController {

    @Autowired
    public SeoContentService seoContentService;

    @Autowired
     public SeoContentRepository rs ;

    @GetMapping("/seo-content")
    public Mono<ResponseEntity<List<SeoContent>>> getAllSeoContents(){
        return Mono.just(ResponseEntity.ok(seoContentService.getListSeoContent()));
    }

    @PostMapping("/seo-content")
    public Mono<ResponseEntity<SeoContent>> createSeoContents(@RequestBody SeoContentRequest seoContentRequest){
        return Mono.just(ResponseEntity.ok(seoContentService.createSeoContents(seoContentRequest)));
    }


    @PutMapping("/seo-content/{id}")
    public Mono<SeoContent> updateSeoContent(@PathVariable Long id , @RequestBody SeoContentRequest seoContentRequest){
        return Mono.just(seoContentService.updateSeoContents(id,seoContentRequest));
    }

    @DeleteMapping("/seo-content/{id}")
    public Mono<ResponseEntity<?>> deleteSeoContentByid(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();
        try {
            SeoContent seoContent = seoContentService.findSeoContentsById(id);
            if(seoContent==null){
                response.put("message", "SeoConten does not exist");
                return Mono.just(ResponseEntity.ok(response));
            }
            seoContentService.deleteSeoContents(id);
            response.put("message", "SeoConten deleted successfully");
            return Mono.just(ResponseEntity.ok(response));
        } catch (UsernameNotFoundException e) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("article not found"));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete article"));
        }
    }

}
