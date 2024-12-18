package com.example.historiaclinica.repository;

import com.example.historiaclinica.model.Especialidad;
import com.example.historiaclinica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByEmail(String email);
    boolean existsByEmail(String email);

    List<Medico> findByEspecialidadesContaining(Especialidad especialidad);
}
