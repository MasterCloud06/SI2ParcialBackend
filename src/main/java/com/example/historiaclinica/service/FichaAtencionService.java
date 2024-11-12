package com.example.historiaclinica.service;

import com.example.historiaclinica.dto.FichaAtencionDto;
import com.example.historiaclinica.dto.FichaAtencionRequestDto;
import com.example.historiaclinica.model.FichaAtencion;
import com.example.historiaclinica.model.Medico;
import com.example.historiaclinica.model.Paciente;
import com.example.historiaclinica.repository.FichaAtencionRepository;
import com.example.historiaclinica.repository.MedicoRepository;
import com.example.historiaclinica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FichaAtencionService {

    @Autowired
    private FichaAtencionRepository fichaAtencionRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public FichaAtencionDto crearFichaAtencion(FichaAtencionRequestDto requestDto) {
        // Verificar si el paciente ya tiene una ficha en la misma fecha
        if (fichaAtencionRepository.existsByFechaAndPacienteId(requestDto.getFecha(), requestDto.getPacienteId())) {
            throw new IllegalArgumentException("El paciente ya tiene una ficha para esta fecha.");
        }

        // Buscar el paciente y el médico
        Paciente paciente = pacienteRepository.findById(requestDto.getPacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con ID: " + requestDto.getPacienteId()));
        
        Medico medico = medicoRepository.findById(requestDto.getMedicoId())
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado con ID: " + requestDto.getMedicoId()));

        // Crear la ficha de atención
        FichaAtencion fichaAtencion = new FichaAtencion();
        fichaAtencion.setPaciente(paciente);
        fichaAtencion.setMedico(medico);
        fichaAtencion.setEspecialidad(medico.getEspecialidades().iterator().next()); // Asumiendo una especialidad
        fichaAtencion.setFecha(requestDto.getFecha());
        fichaAtencion.setHora(requestDto.getHora());
        fichaAtencion.setEstado(requestDto.getEstado());

        FichaAtencion savedFicha = fichaAtencionRepository.save(fichaAtencion);
        return convertToDto(savedFicha);
    }

    public List<FichaAtencionDto> getFichasByMedicoAndFecha(Long medicoId, LocalDate fecha) {
        return fichaAtencionRepository.findByMedicoIdAndFecha(medicoId, fecha).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FichaAtencionDto convertToDto(FichaAtencion fichaAtencion) {
        FichaAtencionDto dto = new FichaAtencionDto();
        dto.setId(fichaAtencion.getId());
        dto.setPacienteId(fichaAtencion.getPaciente().getId());
        dto.setMedicoId(fichaAtencion.getMedico().getId());
        dto.setEspecialidadId(fichaAtencion.getEspecialidad().getIdEspecialidad());
        dto.setFecha(fichaAtencion.getFecha());
        dto.setHora(fichaAtencion.getHora());
        dto.setEstado(fichaAtencion.getEstado());
        return dto;
    }
}
