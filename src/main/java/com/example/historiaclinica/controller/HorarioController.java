package com.example.historiaclinica.controller;

import com.example.historiaclinica.dto.HorarioDto;
import com.example.historiaclinica.model.Horario;
import com.example.historiaclinica.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @PostMapping
    public ResponseEntity<Horario> crearHorario(@Valid @RequestBody HorarioDto horarioDto) {
        Horario horario = horarioService.crearHorario(horarioDto);
        if (horario == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(horario);
    }

    @GetMapping
    public ResponseEntity<List<Horario>> obtenerTodosLosHorarios() {
        List<Horario> horarios = horarioService.obtenerTodosLosHorarios();
        return horarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(horarios);
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<Horario>> obtenerHorariosPorMedico(@PathVariable Long medicoId) {
        List<Horario> horarios = horarioService.obtenerHorariosPorMedico(medicoId);
        return horarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(horarios);
    }
}
