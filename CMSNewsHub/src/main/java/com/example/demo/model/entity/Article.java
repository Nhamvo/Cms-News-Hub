package com.example.demo.model.entity;


import com.example.demo.model.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    private LocalDateTime publishedAt;
    //// new add
    @Column
    private String summary;

    @Column
    private String tag;

    @Column
    private LocalDateTime lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "last_edited_by_id", referencedColumnName = "id")
    private User lastEditedBy;

    @Column
    private String slug;

    @Column
    private String featuredImage;

    @Column
    private Integer view = 0;

    @Column
    private Integer status;

    //
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "content_categories",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;



    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @PrePersist
    protected void onCreate() {
        this.publishedAt = LocalDateTime.now();
    }


}
