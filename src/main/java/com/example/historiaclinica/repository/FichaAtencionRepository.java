package com.example.historiaclinica.repository;

import com.example.historiaclinica.model.FichaAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FichaAtencionRepository extends JpaRepository<FichaAtencion, Long> {

    List<FichaAtencion> findByMedicoIdAndFecha(Long medicoId, LocalDate fecha);

    boolean existsByFechaAndPacienteId(LocalDate fecha, Long pacienteId);
}
