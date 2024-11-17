package com.example.historiaclinica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.historiaclinica.service.UserService;

//import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(userService, jwtTokenProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuración de CORS
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        // Autenticación pública
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/reports/**").permitAll() // Si los reportes son públicos
                        // Rutas específicas protegidas por roles
                        .requestMatchers("/api/audit-logs/**").hasRole("ADMIN")
                        .requestMatchers("/api/users/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/medicos/**").hasRole("MEDICO")
                        .requestMatchers("/api/pacientes/**").hasRole("PACIENTE")
                        .requestMatchers("/api/fichas/**").hasAnyRole("MEDICO", "ADMIN")
                        .requestMatchers("/api/historiaclinica/**").hasAnyRole("MEDICO", "PACIENTE")
                        .requestMatchers("/api/bitacora/**").hasRole("ADMIN")
                        .requestMatchers("/api/especialidades/**").hasAnyRole("ADMIN", "MEDICO")
                        .anyRequest().authenticated() // Proteger el resto de rutas
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Filtro JWT

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://parcialsi2.s3-website.us-east-2.amazonaws.com"); // Agrega el dominio del frontend
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true); // Permitir cookies o tokens en las solicitudes
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    }
