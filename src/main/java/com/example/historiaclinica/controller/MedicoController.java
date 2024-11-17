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

        /**
         * Obtener todos los médicos.
         * GET /api/medicos
         */
        @GetMapping
        public ResponseEntity<List<Medico>> obtenerTodosLosMedicos() {
            List<Medico> medicos = medicoService.obtenerTodosLosMedicos();
            return ResponseEntity.ok(medicos);
        }

        /**
         * Obtener un médico por su ID.
         * GET /api/medicos/{id}
         */
        @GetMapping("/{id}")
        public ResponseEntity<Medico> obtenerMedicoPorId(@PathVariable Long id) {
            Medico medico = medicoService.obtenerMedicoPorId(id);
            if (medico == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(medico);
        }

        /**
         * Crear un nuevo médico.
         * POST /api/medicos
         */
        @PostMapping
        public ResponseEntity<Medico> crearMedico(@Valid @RequestBody MedicoDto medicoDto) {
            Medico medicoGuardado = medicoService.guardarMedico(medicoDto);
            if (medicoGuardado == null) {
                // Asumiendo que el email duplicado es la única razón por la que guardar puede fallar
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(null);
            }
            // Construir la URI del nuevo recurso creado utilizando el ID correcto
            URI location = URI.create(String.format("/api/medicos/%s", medicoGuardado.getIdMedico()));
            return ResponseEntity.created(location).body(medicoGuardado);
        }

        /**
         * Actualizar un médico existente.
         * PUT /api/medicos/{id}
         */
        @PutMapping("/{id}")
        public ResponseEntity<Medico> actualizarMedico(@PathVariable Long id, @Valid @RequestBody MedicoDto medicoDto) {
            Medico medicoActualizado = medicoService.actualizarMedico(id, medicoDto);
            if (medicoActualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(medicoActualizado);
        }

        /**
         * Eliminar un médico por su ID.
         * DELETE /api/medicos/{id}
         */
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarMedico(@PathVariable Long id) {
            Medico medico = medicoService.obtenerMedicoPorId(id);
            if (medico == null) {
                return ResponseEntity.notFound().build();
            }
            medicoService.eliminarMedico(id);
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
