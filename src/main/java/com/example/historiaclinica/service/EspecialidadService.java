package com.example.historiaclinica.service;

import com.example.historiaclinica.dto.EspecialidadDto;
import com.example.historiaclinica.model.Especialidad;
import com.example.historiaclinica.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional(readOnly = true)
    public List<Especialidad> obtenerTodasLasEspecialidades() {
        return especialidadRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Especialidad obtenerEspecialidadPorId(Long id) {
        return especialidadRepository.findById(id).orElse(null);
    }

    @Transactional
    public Especialidad guardarEspecialidad(EspecialidadDto especialidadDto) {
        // Verificar si la especialidad ya existe para prevenir duplicados
        if (especialidadRepository.existsByNombre(especialidadDto.getNombre())) {
            // Si existe, se podría devolver null o manejar de otra forma
            return null;
        }
        Especialidad nuevaEspecialidad = new Especialidad();
        nuevaEspecialidad.setNombre(especialidadDto.getNombre());
        nuevaEspecialidad.setDescripcion(especialidadDto.getDescripcion());
        return especialidadRepository.save(nuevaEspecialidad);
    }

    @Transactional
    public Especialidad actualizarEspecialidad(Long id, EspecialidadDto especialidadDto) {
        Especialidad especialidadExistente = especialidadRepository.findById(id).orElse(null);
        if (especialidadExistente == null) {
            return null; // Manejo de especialidad no encontrada
        }
        especialidadExistente.setNombre(especialidadDto.getNombre());
        especialidadExistente.setDescripcion(especialidadDto.getDescripcion());
        return especialidadRepository.save(especialidadExistente);
    }

    @Transactional
    public void eliminarEspecialidad(Long id) {
        Especialidad especialidad = especialidadRepository.findById(id).orElse(null);
        if (especialidad != null) {
            especialidadRepository.delete(especialidad);
        }
    }
}
