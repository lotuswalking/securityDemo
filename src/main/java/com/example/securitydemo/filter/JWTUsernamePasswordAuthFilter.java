package com.example.securitydemo.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.example.securitydemo.Constants.SecurityConstants.*;

@Log
public class JWTUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    private com.example.securitydemo.util.jwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;


    public JWTUsernamePasswordAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        return super.attemptAuthentication(request, response);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        UserDetails user = (UserDetails) auth.getPrincipal();
        String username = user.getUsername();
//        String token = jwtUtil.generateToken(username);
//        log.info(String.format("Token:%s", token));
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        //        log.info(String.format("Token:%s", token));
        token = TOKEN_PREFIX + token;
        log.info(String.format("function in successfulAuthentication Token:%s", token));
        response.setHeader(HEADER_STRING, token);
        chain.doFilter(request,response);
//        super.successfulAuthentication(request,response,chain,auth);
    }
}
