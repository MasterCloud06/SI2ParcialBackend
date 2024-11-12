package com.example.historiaclinica.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String phoneNumber;   // Número de teléfono
    private String address;       // Dirección
    private String uniqueId;      // ID único
    private String status;        // Estado (por ejemplo, activo, inactivo)
    private int coursesEnroll;    // Número de cursos en los que está inscrito
    private String imageUrl;      // URL de la imagen de perfil

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
