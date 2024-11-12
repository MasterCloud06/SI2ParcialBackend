package com.example.historiaclinica.controller;

import com.example.historiaclinica.dto.EspecialidadDto;
import com.example.historiaclinica.model.Especialidad;
import com.example.historiaclinica.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@Validated
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    /**
     * Obtener todas las especialidades.
     * GET /api/especialidades
     */
    @GetMapping
    public ResponseEntity<List<Especialidad>> obtenerTodasLasEspecialidades() {
        List<Especialidad> especialidades = especialidadService.obtenerTodasLasEspecialidades();
        return ResponseEntity.ok(especialidades);
    }

    /**
     * Obtener una especialidad por su ID.
     * GET /api/especialidades/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> obtenerEspecialidadPorId(@PathVariable Long id) {
        Especialidad especialidad = especialidadService.obtenerEspecialidadPorId(id);
        if (especialidad == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(especialidad);
    }

    /**
     * Crear una nueva especialidad.
     * POST /api/especialidades
     */
    @PostMapping
    public ResponseEntity<Especialidad> crearEspecialidad(@Valid @RequestBody EspecialidadDto especialidadDto) {
        Especialidad especialidadGuardada = especialidadService.guardarEspecialidad(especialidadDto);
        if (especialidadGuardada == null) {
            // Asumiendo que el nombre duplicado es la única razón por la que guardar puede fallar
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        }
        // Construir la URI del nuevo recurso creado utilizando el ID correcto
        URI location = URI.create(String.format("/api/especialidades/%s", especialidadGuardada.getIdEspecialidad()));
        return ResponseEntity.created(location).body(especialidadGuardada);
    }

    /**
     * Actualizar una especialidad existente.
     * PUT /api/especialidades/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> actualizarEspecialidad(@PathVariable Long id, @Valid @RequestBody EspecialidadDto especialidadDto) {
        Especialidad especialidadActualizada = especialidadService.actualizarEspecialidad(id, especialidadDto);
        if (especialidadActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(especialidadActualizada);
    }

    /**
     * Eliminar una especialidad por su ID.
     * DELETE /api/especialidades/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEspecialidad(@PathVariable Long id) {
        Especialidad especialidad = especialidadService.obtenerEspecialidadPorId(id);
        if (especialidad == null) {
            return ResponseEntity.notFound().build();
        }
        especialidadService.eliminarEspecialidad(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Manejo de excepciones de validación.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(Exception ex) {
        // Aquí puedes personalizar el manejo de excepciones de validación
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
