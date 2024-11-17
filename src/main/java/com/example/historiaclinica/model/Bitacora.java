// src/main/java/com/tu_proyecto/model/Bitacora.java
package com.example.historiaclinica.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora")
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accion;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    public Bitacora() {}

    public Bitacora(String accion, LocalDateTime fecha) {
        this.accion = accion;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
