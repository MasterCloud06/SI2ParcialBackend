package com.example.historiaclinica.repository;

import com.example.historiaclinica.model.Horario;
import com.example.historiaclinica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findByMedico(Medico medico);
}
