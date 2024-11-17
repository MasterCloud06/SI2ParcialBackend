package com.example.historiaclinica.dto;

import java.time.LocalDateTime;
import java.util.List;

public class HistoriaClinicaRequestDto {
    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime fechaConsulta;
    private List<String> examenes;
    private byte[] notasConsultaOid; // Ahora es un array de bytes
    private byte[] recetaOid; // Ahora es un array de bytes

    // Getters y Setters
    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public LocalDateTime getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDateTime fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public List<String> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<String> examenes) {
        this.examenes = examenes;
    }

    public byte[] getNotasConsultaOid() {
        return notasConsultaOid;
    }

    public void setNotasConsultaOid(byte[] notasConsultaOid) {
        this.notasConsultaOid = notasConsultaOid;
    }

    public byte[] getRecetaOid() {
        return recetaOid;
    }

    public void setRecetaOid(byte[] recetaOid) {
        this.recetaOid = recetaOid;
    }
}
