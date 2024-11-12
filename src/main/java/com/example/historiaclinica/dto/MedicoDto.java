package com.example.historiaclinica.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Data
public class MedicoDto {
    @NotBlank(message = "El nombre del médico es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido del médico es obligatorio")
    @Size(max = 100, message = "El apellido no debe exceder los 100 caracteres")
    private String apellido;

    @NotBlank(message = "El email del médico es obligatorio")
    @Email(message = "El email debe ser válido")
    @Size(max = 150, message = "El email no debe exceder los 150 caracteres")
    private String email;

    @Size(max = 20, message = "El teléfono no debe exceder los 20 caracteres")
    private String telefono;

    private Set<Long> especialidadIds; // IDs de las especialidades asociadas
}
