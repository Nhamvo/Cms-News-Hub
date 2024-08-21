package com.example.demo.model.repository;

import com.example.demo.model.entity.SeoContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeoContentRepository extends JpaRepository<SeoContent,Long> {
 }
