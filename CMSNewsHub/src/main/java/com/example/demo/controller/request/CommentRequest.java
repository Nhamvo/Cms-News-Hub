package com.example.demo.controller.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentRequest {
    private Long user;
    private Long article;
    private String comment;
    private Integer rating;
}
