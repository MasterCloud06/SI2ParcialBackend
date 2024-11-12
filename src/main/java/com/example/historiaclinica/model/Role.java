package com.example.historiaclinica.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

    // Constructor que acepta RoleName
    public Role(RoleName name) {
        this.name = name;
    }

    // Constructor sin argumentos (requerido por JPA)
    public Role() {
    }
}
