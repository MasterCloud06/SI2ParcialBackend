// src/main/java/com/tu_proyecto/dto/BitacoraDTO.java
package com.example.historiaclinica.dto;

import java.time.LocalDateTime;

public class BitacoraDto {

    private String accion;
    private LocalDateTime fecha;

    public BitacoraDto() {}

    public BitacoraDto(String accion, LocalDateTime fecha) {
        this.accion = accion;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
