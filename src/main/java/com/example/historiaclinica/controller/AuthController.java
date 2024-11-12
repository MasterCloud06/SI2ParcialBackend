package com.example.historiaclinica.controller;

import com.example.historiaclinica.config.JwtTokenProvider;
import com.example.historiaclinica.model.Users;
import com.example.historiaclinica.model.Role;
import com.example.historiaclinica.model.RoleName;
import com.example.historiaclinica.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          PasswordEncoder passwordEncoder,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody RegisterRequest registerRequest) {
        Users user = new Users();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(RoleName.USUARIO)); // Rol por defecto
        user.setRoles(roles);

        Users newUser = userService.registerUser(user, roles);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Genera el token JWT usando JwtTokenProvider
        String token = jwtTokenProvider.generateToken(loginRequest.getUsername());

        // Obtiene los roles del usuario autenticado
        Set<String> roles = authentication.getAuthorities().stream()
                                          .map(role -> role.getAuthority())
                                          .collect(Collectors.toSet());

        // Construye la respuesta JSON con el token y roles
        Map<String, Object> response = new HashMap<>();
        response.put("token", "Bearer " + token);  // Formato esperado por el frontend
        response.put("roles", roles);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public String logout() {
        return "Sesión cerrada exitosamente";
    }
}

// Clase para la solicitud de registro
class RegisterRequest {
    private String username;
    private String email;
    private String password;

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

// Clase para la solicitud de inicio de sesión
class LoginRequest {
    private String username;
    private String password;

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
