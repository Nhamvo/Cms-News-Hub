package com.example.demo.model.service;


import com.example.demo.controller.request.CommentRequest;
import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    Comment createComments (CommentRequest comment);
    List<CommentDTO> getCommentsByUserId (Long id);
    List<CommentDTO> getCommentsByArticleId (Long id);
}
