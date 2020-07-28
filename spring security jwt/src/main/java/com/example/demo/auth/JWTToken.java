package com.example.demo.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTToken {

    private static final String secret_Key = "tuncaysahinTheEllipticCurveDigitalSignatureAlgorithm";
    private static final long validity = 5 * 60 * 60 * 1000;

    public String generateToke(String username){
        System.out.println("Token üretildi");
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("www.localhost.bu")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+validity))
                .signWith(SignatureAlgorithm.HS256,secret_Key)
                .compact();
    }

    public boolean validate(String token){
        Claims body = Jwts.parser().setSigningKey(secret_Key).parseClaimsJws(token).getBody();

        return body.getExpiration().after(new Date());
    }

    public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(secret_Key).parseClaimsJws(token).getBody().getSubject();
    }

}
