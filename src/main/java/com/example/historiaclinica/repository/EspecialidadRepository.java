package com.example.historiaclinica.repository;

import com.example.historiaclinica.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    Optional<Especialidad> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
