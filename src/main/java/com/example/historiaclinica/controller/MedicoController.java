package com.example.historiaclinica.controller;

import com.example.historiaclinica.dto.MedicoDto;
import com.example.historiaclinica.model.Medico;
import com.example.historiaclinica.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@Validated
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<Medico>> obtenerTodosLosMedicos() {
        List<Medico> medicos = medicoService.obtenerTodosLosMedicos();
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtenerMedicoPorId(@PathVariable Long id) {
        Medico medico = medicoService.obtenerMedicoPorId(id);
        if (medico == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medico);
    }

    @PostMapping
    public ResponseEntity<Medico> crearMedico(@Valid @RequestBody MedicoDto medicoDto) {
        Medico medicoGuardado = medicoService.guardarMedico(medicoDto);
        if (medicoGuardado == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        URI location = URI.create(String.format("/api/medicos/%s", medicoGuardado.getIdMedico()));
        return ResponseEntity.created(location).body(medicoGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizarMedico(@PathVariable Long id, @Valid @RequestBody MedicoDto medicoDto) {
        Medico medicoActualizado = medicoService.actualizarMedico(id, medicoDto);
        if (medicoActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medicoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoService.obtenerMedicoPorId(id);
        if (medico == null) {
            return ResponseEntity.notFound().build();
        }
        medicoService.eliminarMedico(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/especialidad/{especialidadId}")
    public ResponseEntity<List<Medico>> obtenerMedicosPorEspecialidad(@PathVariable Long especialidadId) {
        List<Medico> medicos = medicoService.obtenerMedicosPorEspecialidad(especialidadId);
        return medicos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(medicos);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
