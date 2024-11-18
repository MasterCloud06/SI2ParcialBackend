package com.example.historiaclinica.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class FichaAtencionDto {
    private Long id;
    private String pacienteNombre; // Cambiado a String
    private String medicoNombre; // Cambiado a String
    private String especialidadNombre; // Cambiado a String
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;

    // Constructor con todos los parámetros
    public FichaAtencionDto(Long id, String pacienteNombre, String medicoNombre, String especialidadNombre,
                            LocalDate fecha, LocalTime hora, String estado) {
        this.id = id;
        this.pacienteNombre = pacienteNombre;
        this.medicoNombre = medicoNombre;
        this.especialidadNombre = especialidadNombre;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public String getMedicoNombre() {
        return medicoNombre;
    }

    public void setMedicoNombre(String medicoNombre) {
        this.medicoNombre = medicoNombre;
    }

    public String getEspecialidadNombre() {
        return especialidadNombre;
    }

    public void setEspecialidadNombre(String especialidadNombre) {
        this.especialidadNombre = especialidadNombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
