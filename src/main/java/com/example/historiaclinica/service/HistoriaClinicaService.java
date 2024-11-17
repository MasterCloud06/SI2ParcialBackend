package com.example.historiaclinica.service;

import com.example.historiaclinica.dto.HistoriaClinicaDto;
import com.example.historiaclinica.dto.HistoriaClinicaRequestDto;
import com.example.historiaclinica.model.HistoriaClinica;
import com.example.historiaclinica.model.Medico;
import com.example.historiaclinica.model.Paciente;
import com.example.historiaclinica.repository.HistoriaClinicaRepository;
import com.example.historiaclinica.repository.MedicoRepository;
import com.example.historiaclinica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoriaClinicaService {

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public List<HistoriaClinicaDto> getHistoriaClinicaByPaciente(Long pacienteId) {
        return historiaClinicaRepository.findByPacienteId(pacienteId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public HistoriaClinicaDto createHistoriaClinica(HistoriaClinicaRequestDto requestDto) {
        // Busca el paciente y el médico por sus IDs
        Paciente paciente = pacienteRepository.findById(requestDto.getPacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con ID: " + requestDto.getPacienteId()));

        Medico medico = medicoRepository.findById(requestDto.getMedicoId())
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado con ID: " + requestDto.getMedicoId()));

        // Crea una nueva instancia de HistoriaClinica y asigna los objetos paciente y médico
        HistoriaClinica historiaClinica = new HistoriaClinica();
        historiaClinica.setPaciente(paciente);
        historiaClinica.setMedico(medico);
        historiaClinica.setFechaConsulta(requestDto.getFechaConsulta());
        historiaClinica.setExamenes(requestDto.getExamenes());
        
        // Asigna los byte[] directamente
        historiaClinica.setNotasConsultaOid(requestDto.getNotasConsultaOid());
        historiaClinica.setRecetaOid(requestDto.getRecetaOid());
        
        HistoriaClinica saved = historiaClinicaRepository.save(historiaClinica);
        return convertToDto(saved);
    }

    private HistoriaClinicaDto convertToDto(HistoriaClinica historiaClinica) {
        HistoriaClinicaDto dto = new HistoriaClinicaDto();
        dto.setId(historiaClinica.getId());
        dto.setPacienteId(historiaClinica.getPaciente().getId());
        dto.setMedicoId(historiaClinica.getMedico().getId());
        dto.setFechaConsulta(historiaClinica.getFechaConsulta());
        dto.setExamenes(historiaClinica.getExamenes());

        // Convierte byte[] a String si es necesario, o maneja como byte[]
        dto.setNotasConsultaOid(historiaClinica.getNotasConsultaOid());
        dto.setRecetaOid(historiaClinica.getRecetaOid());
        return dto;
    }
}
