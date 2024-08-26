package com.example.demo.model.repository;


import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>, JpaSpecificationExecutor<Comment> {


    @Query( value = """
            select new com.example.demo.model.dto.CommentDTO(c.id, c.user.id,c.article.id,
            c.comment, c.rating,c.commentDate,c.user.userName, c.article.title) 
            from Comment c where c.user.id =:id
            """)
    List<CommentDTO> findAllByUserId(Long id);


    @Query( value = """
            select new com.example.demo.model.dto.CommentDTO(c.id, c.user.id,c.article.id,
            c.comment, c.rating,c.commentDate,c.user.userName, c.article.title)
            from Comment c where c.article.id =:id
            """)
    List<CommentDTO> findAllByArticleId(Long id);
}
