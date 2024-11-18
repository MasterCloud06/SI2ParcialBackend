package com.example.historiaclinica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "horarios")
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dia;

    @Column(nullable = false)
    private String turno;

    @Column(nullable = false)
    private String modoConsulta;

    @Column(nullable = false)
    private java.time.LocalTime horaInicio;

    @Column(nullable = false)
    private java.time.LocalTime horaFin;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
}
