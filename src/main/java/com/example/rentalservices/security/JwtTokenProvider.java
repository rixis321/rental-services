package com.example.rentalservices.security;

import com.example.rentalservices.model.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${app-jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;


    public String generateToken(Authentication authentication, UserAuth userAuth) {
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime() + jwtExpirationDate);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userAuth.getUuid());
        claims.put("role", userAuth.getRole());

        return Jwts.builder()
                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    // Pobranie e-maila (subject) z tokena JWT
    public String getEmail(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject(); // Subject w tym wypadku to e-mail użytkownika
    }

    // Pobranie roli z tokena JWT
    public String getRole(String token) {
        Claims claims = getClaimsFromToken(token);
        return (String) claims.get("role");
    }

    // Walidacja tokena
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
           // throw new (HttpStatus.BAD_REQUEST,"Invalid JWT token");
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
           // throw new (HttpStatus.BAD_REQUEST,"Expired JWT token");
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            //throw new (HttpStatus.BAD_REQUEST,"Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            //throw new (HttpStatus.BAD_REQUEST,"JWT claims string is empty");
        }
        return false;
    }

    // Pobranie `Claims` z tokena JWT
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }
}