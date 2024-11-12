package com.example.historiaclinica.repository;

import com.example.historiaclinica.model.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {
    List<HistoriaClinica> findByPacienteId(Long pacienteId);
    List<HistoriaClinica> findByMedicoId(Long medicoId);
}
