package com.example.historiaclinica.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Usa una clave secreta segura. Puedes almacenarla en una variable de entorno.
   // private final String SECRET_KEY = "hgs@LY4fj/1wFU#b|GkDp7[8F\"3r=T[@-qWAlA]p@D44HjqU(\"Dy!l3,/eaXA'HSyb[9mO7l;|*g+5DGyw\"B-::j*d9]*0FLKo!Z42#k1VIaGbFXo8)BK\\c+fKy1o1US#IOi{fbH}q5-6Myl9d0!R36YI.vHgbK8{mrvwL2}hbL7'\\kV4x.1O-{CL;HJQ=|Hvve?P!6fs5z|7-ZYXY!*WIQ4A@D4=i]Qx2P{TKU7mCCa0qO\\2)+HJhlP!ym\"l-0S"; // Debe tener al menos 256 bits para HS256
    private final long JWT_EXPIRATION_MS = 86400000; // 24 horas

    private final Key key;

    public JwtTokenProvider() {
        // Genera la clave de forma segura
      //  this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // Método para generar un token JWT
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(username)
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
}
