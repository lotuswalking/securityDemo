package com.example.securitydemo.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class MyUserDetails implements UserDetails {
    private String username;
    private String password;
    private String email;
    private boolean active;
    private List<GrantedAuthority> authorities;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public MyUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
//        this.password = passwordEncoder.encode(user.getPassword());
//        this.emailAddress = user.getEmailAddress();
        this.active = user.isActive();
        this.email = user.getEmailAddress();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
//        this.authorities = tmpAuthorities;
//        this.authorities = Arrays.stream(user.getRoles().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
