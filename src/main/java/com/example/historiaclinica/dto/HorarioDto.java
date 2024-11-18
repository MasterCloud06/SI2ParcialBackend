package com.example.historiaclinica.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

@Data
public class HorarioDto {
    @NotBlank(message = "El día es obligatorio")
    private String dia;

    @NotBlank(message = "El turno es obligatorio")
    private String turno;

    @NotBlank(message = "El modo de consulta es obligatorio")
    private String modoConsulta;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotNull(message = "El id del médico es obligatorio")
    private Long medicoId;
}
