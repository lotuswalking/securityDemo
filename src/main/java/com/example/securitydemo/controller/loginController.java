package com.example.securitydemo.controller;

import com.example.securitydemo.entity.AuthRequest;
import com.example.securitydemo.entity.User;
import com.example.securitydemo.util.jwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
@RequiredArgsConstructor
public class loginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    private final jwtUtil jwtUtil;
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid User request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

//            User user = (User) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(request.getUsername()))
                    .body(request);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
