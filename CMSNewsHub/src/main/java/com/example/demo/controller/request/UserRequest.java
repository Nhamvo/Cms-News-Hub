package com.example.demo.controller.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {

     private String userName ;

    private String passWord;

    private String email;

    private List<Long> roles;
}
