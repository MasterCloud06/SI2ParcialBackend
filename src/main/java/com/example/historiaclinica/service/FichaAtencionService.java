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
        // Validar si el paciente ya tiene una ficha en la misma fecha
        if (fichaAtencionRepository.existsByFechaAndPacienteId(requestDto.getFecha(), requestDto.getPacienteId())) {
            throw new IllegalArgumentException("El paciente ya tiene una ficha para esta fecha.");
        }

        // Obtener el paciente y el médico
        Paciente paciente = pacienteRepository.findById(requestDto.getPacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con ID: " + requestDto.getPacienteId()));

        Medico medico = medicoRepository.findById(requestDto.getMedicoId())
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado con ID: " + requestDto.getMedicoId()));

        // Crear la ficha de atención
        FichaAtencion fichaAtencion = new FichaAtencion();
        fichaAtencion.setPaciente(paciente);
        fichaAtencion.setMedico(medico);

        // Asegurar que haya al menos una especialidad antes de asignarla
        if (!medico.getEspecialidades().isEmpty()) {
            fichaAtencion.setEspecialidad(medico.getEspecialidades().iterator().next());
        } else {
            throw new IllegalArgumentException("El médico no tiene especialidades asignadas.");
        }

        fichaAtencion.setFecha(requestDto.getFecha());
        fichaAtencion.setHora(requestDto.getHora());
        fichaAtencion.setEstado(requestDto.getEstado());

        // Guardar la ficha y convertirla a DTO
        FichaAtencion savedFicha = fichaAtencionRepository.save(fichaAtencion);
        return convertToDto(savedFicha);
    }
    public FichaAtencionDto getFichaById(Long fichaId) {
        FichaAtencion ficha = fichaAtencionRepository.findById(fichaId)
                .orElseThrow(() -> new IllegalArgumentException("Ficha no encontrada con ID: " + fichaId));
    
        return convertToDto(ficha);
    }
    

    public List<FichaAtencionDto> getAllFichas() {
        return fichaAtencionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<FichaAtencionDto> getFichasByMedicoAndFecha(Long medicoId, LocalDate fecha) {
        return fichaAtencionRepository.findByMedicoIdAndFecha(medicoId, fecha).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FichaAtencionDto convertToDto(FichaAtencion fichaAtencion) {
        return new FichaAtencionDto(
                fichaAtencion.getId(),
                fichaAtencion.getPaciente().getNombre(), // Nombre del paciente
                fichaAtencion.getMedico().getNombre(),   // Nombre del médico
                fichaAtencion.getEspecialidad().getNombre(), // Nombre de la especialidad
                fichaAtencion.getFecha(),
                fichaAtencion.getHora(),
                fichaAtencion.getEstado()
        );
    }
    
}
