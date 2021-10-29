package com.example.securitydemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @NonNull
    @Column(name="username")
    private String username;
    @NonNull
    @Column(name="password")
    private String password;
    @Column(name = "email")
    private String emailAddress;
    private boolean active;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
//    @Value("${jwt.secret}")
//    private String secret;
}
