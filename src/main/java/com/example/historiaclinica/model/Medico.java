package com.example.historiaclinica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medicos", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "uc_medico_email")
})
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Long idMedico;

    @NotBlank(message = "El nombre del médico es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido del médico es obligatorio")
    @Size(max = 100, message = "El apellido no debe exceder los 100 caracteres")
    @Column(nullable = false)
    private String apellido;

    @NotBlank(message = "El email del médico es obligatorio")
    @Email(message = "El email debe ser válido")
    @Size(max = 150, message = "El email no debe exceder los 150 caracteres")
    @Column(nullable = false, unique = true)
    private String email;

    @Size(max = 20, message = "El teléfono no debe exceder los 20 caracteres")
    private String telefono;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "medico_especialidad",
            joinColumns = @JoinColumn(name = "id_medico"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidad"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_medico", "id_especialidad"}, name = "uc_medico_especialidad"))
    private Set<Especialidad> especialidades = new HashSet<>();

    // Método adicional para consistencia con getId()
    public Long getId() {
        return idMedico;
    }
}
