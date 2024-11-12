package com.example.historiaclinica.service;

import com.example.historiaclinica.dto.MedicoDto;
import com.example.historiaclinica.model.Especialidad;
import com.example.historiaclinica.model.Medico;
import com.example.historiaclinica.repository.EspecialidadRepository;
import com.example.historiaclinica.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional(readOnly = true)
    public List<Medico> obtenerTodosLosMedicos() {
        return medicoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Medico obtenerMedicoPorId(Long id) {
        // Usando Optional para manejar la respuesta sin excepciones
        return medicoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Medico guardarMedico(MedicoDto medicoDto) {
        // Verificar si el médico ya existe para prevenir duplicados
        if (medicoRepository.existsByEmail(medicoDto.getEmail())) {
            // Si existe, se podría devolver null o manejar de otra forma
            return null;
        }
        Medico nuevoMedico = new Medico();
        nuevoMedico.setNombre(medicoDto.getNombre());
        nuevoMedico.setApellido(medicoDto.getApellido());
        nuevoMedico.setEmail(medicoDto.getEmail());
        nuevoMedico.setTelefono(medicoDto.getTelefono());
        asignarEspecialidadesAMedico(medicoDto, nuevoMedico);
        return medicoRepository.save(nuevoMedico);
    }

    @Transactional
    public Medico actualizarMedico(Long id, MedicoDto medicoDto) {
        Medico medicoExistente = medicoRepository.findById(id).orElse(null);
        if (medicoExistente == null) {
            return null; // Manejo de medico no encontrado
        }
        medicoExistente.setNombre(medicoDto.getNombre());
        medicoExistente.setApellido(medicoDto.getApellido());
        medicoExistente.setEmail(medicoDto.getEmail());
        medicoExistente.setTelefono(medicoDto.getTelefono());
        asignarEspecialidadesAMedico(medicoDto, medicoExistente);
        return medicoRepository.save(medicoExistente);
    }

    @Transactional
    public void eliminarMedico(Long id) {
        Medico medico = medicoRepository.findById(id).orElse(null);
        if (medico != null) {
            medicoRepository.delete(medico);
        }
    }

    private void asignarEspecialidadesAMedico(MedicoDto medicoDto, Medico medico) {
        if (medicoDto.getEspecialidadIds() != null && !medicoDto.getEspecialidadIds().isEmpty()) {
            Set<Especialidad> especialidades = especialidadRepository.findAllById(medicoDto.getEspecialidadIds())
                .stream().collect(Collectors.toSet());
            if (especialidades.size() == medicoDto.getEspecialidadIds().size()) {
                medico.setEspecialidades(especialidades);
            } else {
                // Lógica si no se encuentran todas las especialidades, opcionalmente manejar este caso
            }
        } else {
            medico.setEspecialidades(new HashSet<>()); // Limpiar especialidades si no se proporcionan nuevas
        }
    }
}
