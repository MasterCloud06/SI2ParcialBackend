package com.example.historiaclinica.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.historiaclinica.model.Users;
import com.example.historiaclinica.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    // Es recomendable almacenar la clave en variables de entorno o un almacén seguro
    private final String SECRET_KEY = "hgs@LY4fj/1wFU#b|GkDp7[8F\"3r=T[@-qWAlA]p@D44HjqU(\"Dy!l3,/eaXA'HSyb[9mO7l;|*g+5DGyw\"B-::j*d9]*0FLKo!Z42#k1VIaGbFXo8)BK\\c+fKy1o1US#IOi{fbH}q5-6Myl9d0!R36YI.vHgbK8{mrvwL2}hbL7'\\kV4x.1O-{CL;HJQ=|Hvve?P!6fs5z|7-ZYXY!*WIQ4A@D4=i]Qx2P{TKU7mCCa0qO\\2)+HJhlP!ym\"l-0S"; // Debe tener al menos 256 bits para HS256

    private final JwtParser jwtParser;

    public JwtAuthenticationFilter() {
        // Genera la clave de forma segura
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                Jws<Claims> jwsClaims = jwtParser.parseClaimsJws(token);
                Claims claims = jwsClaims.getBody();

                String username = claims.getSubject();
                if (username != null) {
                    // Cargar el usuario completo desde el servicio
                    Users user = userService.findByUsername(username)
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                    // Convertir los roles del usuario en autoridades
                    List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                            .collect(Collectors.toList());

                    // Crear la autenticación con autoridades
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException e) {
                // Manejar errores de JWT (token inválido, expirado, etc.)
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
