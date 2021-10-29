package com.example.securitydemo.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.securitydemo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import sun.plugin2.applet.Plugin2ClassLoader;

import java.util.ArrayList;
import java.util.Date;

import static com.example.securitydemo.Constants.SecurityConstants.EXPIRATION_TIME;
import static com.example.securitydemo.Constants.SecurityConstants.TOKEN_PREFIX;

@Component
@RequiredArgsConstructor
public class jwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    public String generateToken(String username) {
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secretKey.getBytes()));
        return TOKEN_PREFIX+token;

    }

    public UsernamePasswordAuthenticationToken parseToken(String token) {
        if (token != null) {
            String username = JWT.require(Algorithm.HMAC512(secretKey.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            }
            return null;
        }
        return null;

    }


}
