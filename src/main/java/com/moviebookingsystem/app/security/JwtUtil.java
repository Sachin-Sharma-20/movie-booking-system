package com.moviebookingsystem.app.security;


import com.moviebookingsystem.app.constants.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyStore;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String PRIVATE_KEY = "my_name_is_sach_and_I_play_bgmi_.";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(PRIVATE_KEY.getBytes());
    }


    public Claims extractClaim(String token) {
         return  Jwts.parser()
                 .setSigningKey(getSigningKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
    }

    public String generateToken(UserDetails userDetails) {
        String role = userDetails.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("ROLE_USER");
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return userDetails.getUsername().equals(username) && !isTokenExpired(token);
    }

    public String getUsernameFromToken(String token) {
        return extractClaim(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token).getExpiration().before(new Date());
    }

    public String getRole(String token) {
        String role =  extractClaim(token).get("role", String.class);
        return role;
    }
}
