package com.example.securitydemo.controller;

import com.example.securitydemo.entity.User;
import com.example.securitydemo.respo.UserRepository;
//import com.example.securitydemo.util.jwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;


@Controller
public class homeController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public String Home() {
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

//    @Autowired
//    private jwtUtil jwtUtil;

    @GetMapping("/hello")
    public String hello(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        User user = userRepository.findByUsername(principal.getName());
//        res.setHeader(HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(user.getUsername()));
        model.addAttribute("auth",user);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(request.getUsername()))
//                .body(request);
        return "hello";
    }


}
