package com.rakesh.bms.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {


        private static final String SECRET_KEY = "12345678901234567890123456789012";

        public String generateToken(String email) {
            return Jwts
                    .builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)
                    .compact();
        }

        public String extractEmail(String token) {
            return extractAllClaims(token).getSubject();
        }

        public boolean isTokenValid(String token, String email) {
            String tokenEmail = extractEmail(token);
            return tokenEmail.equals(email) && !isTokenExpired(token);
        }

        private boolean isTokenExpired(String token) {
            return extractAllClaims(token).getExpiration().before(new Date());
        }

        private Claims extractAllClaims(String token) {
            return Jwts
                    .parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

        private Key getSignKey() {
            byte[] keyBytes = SECRET_KEY.getBytes();
            return Keys.hmacShaKeyFor(keyBytes);
        }
    }



