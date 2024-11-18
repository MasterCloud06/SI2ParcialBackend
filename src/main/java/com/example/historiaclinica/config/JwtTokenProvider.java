package com.example.historiaclinica.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Genera una clave segura automáticamente
    private final long JWT_EXPIRATION_MS = 86400000; // 24 horas de expiración

    // Método para generar un token JWT
    public String generateToken(String username, List<String> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);
    
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles) // Agregar roles como claim
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    // Método para validar el token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Maneja excepciones de token inválido o expirado
            return false;
        }
    }

    // Método para obtener el nombre de usuario a partir del token JWT
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Método para obtener los roles desde el token JWT
    
    public List<String> getRolesFromToken(String token) {
        Object roles = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles");
    
        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream()
                    .filter(role -> role instanceof String)
                    .map(Object::toString)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Roles no válidos en el token");
        }
    }
    }
