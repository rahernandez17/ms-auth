package com.example.auth.security.components;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
@Log4j2
public class TokenUtilComponent {

    @Value("${jwt.access-token.secret}")
    private String secret;

    @Value("${jwt.access-token.validity-seconds}")
    private Long validitySeconds;

    public String generateToken(Map<String, Object> claims, String username) {
        long expirationTime = validitySeconds * 1000L;

        return Jwts.builder()
                .setId(String.valueOf(UUID.randomUUID()))
                .setSubject(username)
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException | ExpiredJwtException | IllegalArgumentException e) {
            log.error("Error: {}", e.getMessage());
            return null;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        if (Objects.isNull(claims)) {
            return null;
        }

        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean isTokenValid(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return tokenUsername.equals(username) && !isTokenExpired(token);
    }

    public Boolean isTokenExpired(String token) {
        Date date = extractExpiration(token);

        if (Objects.isNull(date)) {
            return true;
        }

        return extractExpiration(token).before(new Date());
    }
}
