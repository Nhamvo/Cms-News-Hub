package com.example.demo.model.entity.user;

//import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.SavedArticle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name= "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String userName ;

    @Column(nullable = false)
    private String passWord;

    @Column(nullable = false,unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

   @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
   private Set<SavedArticle> savedArticles;


    public String getRoleNames() {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.joining(", "));
    }


}
