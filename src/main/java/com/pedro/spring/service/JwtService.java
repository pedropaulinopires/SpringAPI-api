package com.pedro.spring.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pedro.spring.domain.Users;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final int TIME_EXPIRES = 1000;
    public static final String TOKEN_PASSWORD = "41989389-700f-4349-9a1d-5eb5e98ffc8a";
    public String generatedToken(Users user){
        return JWT.create().withSubject(user.getId().toString()).withExpiresAt(new Date(new Date().getTime()+TIME_EXPIRES)).sign(
                Algorithm.HMAC512(TOKEN_PASSWORD)
        );
    }

    public String readToken(String token){
        String tokenValue = JWT.require(Algorithm.HMAC512(TOKEN_PASSWORD)).build().verify(token).getSubject();
        return tokenValue;
    }

}
