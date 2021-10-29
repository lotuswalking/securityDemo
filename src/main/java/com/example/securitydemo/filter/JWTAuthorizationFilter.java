package com.example.securitydemo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.securitydemo.entity.User;
import com.example.securitydemo.util.jwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.classfile.ConstantString;
import lombok.extern.java.Log;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.plugin2.applet.Plugin2ClassLoader;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.securitydemo.Constants.SecurityConstants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Log


public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private com.example.securitydemo.util.jwtUtil jwtUtil;

    private AuthenticationManager authenticationManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain);
        if(request.getServletPath().equals("/api/login")) {
            log.info("doFilterInternal in path /api/login");
            chain.doFilter(request,response);
        }else{
            String header = request.getHeader(AUTHORIZATION);
            if(header != null && header.startsWith(TOKEN_PREFIX)) {
                String token = header.substring(TOKEN_PREFIX.length());
                log.info(String.format("function in successfulAuthentication Token:%s", token));
                UsernamePasswordAuthenticationToken userToken = jwtUtil.parseToken(token);
                SecurityContextHolder.getContext().setAuthentication(userToken);

            }
            log.info("doFilterInternal in path /api/login end ***************");
            chain.doFilter(request,response);


        }

    }
   /* private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        log.info(String.format("*********Print the token: %s",token));
        if(token != null) {
            //parse the token
            return jwtUtil.parseToken(token);
*//*            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX,""))
                    .getSubject();
            log.info(String.format("*********Print the User: %s",user));
            if(user!=null) {
//                new arraylist means authorities
                return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
            }
            return null;*//*
        }
        return null;
    }*/
}
