package com.example.historiaclinica.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    private LocalDateTime fechaConsulta;

    @ElementCollection
    private List<String> examenes; // Lista de exámenes como nombres de archivos o enlaces

    @Lob
    @Column(name = "notas_consulta_oid", columnDefinition = "oid")
    private byte[] notasConsultaOid; // Notas o informe del médico en formato binario

    @Lob
    @Column(name = "receta_oid", columnDefinition = "oid")
    private byte[] recetaOid; // Receta o tratamiento del paciente en formato binario

    public HistoriaClinica() {}

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
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
