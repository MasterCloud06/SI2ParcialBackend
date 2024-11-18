package com.example.historiaclinica.service;

import com.example.historiaclinica.dto.HorarioDto;
import com.example.historiaclinica.model.Horario;
import com.example.historiaclinica.model.Medico;
import com.example.historiaclinica.repository.HorarioRepository;
import com.example.historiaclinica.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HorarioService {
    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public Horario crearHorario(HorarioDto horarioDto) {
        Medico medico = medicoRepository.findById(horarioDto.getMedicoId()).orElse(null);
        if (medico == null) {
            return null; // Manejar mejor el error
        }

        Horario horario = new Horario();
        horario.setDia(horarioDto.getDia());
        horario.setTurno(horarioDto.getTurno());
        horario.setModoConsulta(horarioDto.getModoConsulta());
        horario.setHoraInicio(horarioDto.getHoraInicio());
        horario.setHoraFin(horarioDto.getHoraFin());
        horario.setMedico(medico);

        return horarioRepository.save(horario);
    }

    @Transactional(readOnly = true)
    public List<Horario> obtenerTodosLosHorarios() {
        return horarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Horario> obtenerHorariosPorMedico(Long medicoId) {
        Medico medico = medicoRepository.findById(medicoId).orElse(null);
        if (medico != null) {
            return horarioRepository.findByMedico(medico);
        }
        return List.of();
    }
}
