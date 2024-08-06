package com.example.demo.controller;

import com.example.demo.model.dto.SeoContentRequest;
import com.example.demo.model.dto.UserRequest;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.SeoContent;
import com.example.demo.model.entity.user.User;
import com.example.demo.model.repository.SeoContentRepository;
import com.example.demo.model.service.CategoryService;
import com.example.demo.model.service.SeoContentService;
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
public class SeoContentController {

    @Autowired
    public SeoContentService seoContentService;

    @Autowired
     public SeoContentRepository rs ;

    @GetMapping("/seo-content")
    public ResponseEntity<List<SeoContent>> getAllSeoContents(){
        return ResponseEntity.ok(seoContentService.getListSeoContent());
    }
    @PostMapping("/seo-content")
    public ResponseEntity<SeoContent> createSeoContents(@RequestBody SeoContentRequest seoContentRequest){
        SeoContent seoContentResuilt = seoContentService.createSeoContents(seoContentRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/seo-content/" + seoContentResuilt.getSeoId()));
        return new ResponseEntity<>(seoContentResuilt,httpHeaders, HttpStatus.CREATED);
    }


    @PutMapping("/seo-content/{id}")
    public ResponseEntity<SeoContent> updateSeoContent(@PathVariable Long id , @RequestBody SeoContentRequest seoContentRequest){
       SeoContent seoContent = seoContentService.updateSeoContents(id,seoContentRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/seo-content/" + seoContent.getSeoId()));
        return new ResponseEntity<>(seoContent,httpHeaders,HttpStatus.CREATED);
    }

    @DeleteMapping("/seo-content/{id}")
    public ResponseEntity<?> deleteSeoContentByid(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();

        try {
            SeoContent seoContent = seoContentService.findSeoContentsById(id);
            if(seoContent==null){
                response.put("message", "SeoConten does not exist");
                return ResponseEntity.ok(response);
            }
            seoContentService.deleteSeoContents(id);
            response.put("message", "SeoConten deleted successfully");
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete user");
        }
    }

}
