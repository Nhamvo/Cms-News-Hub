package com.example.demo.model.service.impl;

import com.example.demo.controller.request.CommentRequest;
import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.user.User;
import com.example.demo.model.repository.ArticleRepository;
import com.example.demo.model.repository.CommentRepository;
import com.example.demo.model.repository.UserRepository;
import com.example.demo.model.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;


    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Comment createComments(CommentRequest commentRequest) {
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        User user = userRepository.findById(commentRequest.getUser()).orElse(null);
        Article article = articleRepository.findById(commentRequest.getArticle()).orElse(null);
        comment.setUser(user);
        comment.setArticle(article);
        return commentRepository.save(comment);
    }

    @Override
    public List<CommentDTO> getCommentsByUserId(Long id) {
        return commentRepository.findAllByUserId(id);
    }

    @Override
    public List<CommentDTO> getCommentsByArticleId(Long id) {
        return commentRepository.findAllByArticleId(id);
    }


}
