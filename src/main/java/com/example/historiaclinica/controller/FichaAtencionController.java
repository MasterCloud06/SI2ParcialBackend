package com.example.historiaclinica.controller;

import com.example.historiaclinica.dto.FichaAtencionDto;
import com.example.historiaclinica.dto.FichaAtencionRequestDto;
import com.example.historiaclinica.service.FichaAtencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/fichas")
public class FichaAtencionController {

    @Autowired
    private FichaAtencionService fichaAtencionService;

    @PostMapping
    public ResponseEntity<FichaAtencionDto> crearFichaAtencion(@RequestBody FichaAtencionRequestDto requestDto) {
        FichaAtencionDto fichaAtencionDto = fichaAtencionService.crearFichaAtencion(requestDto);
        return ResponseEntity.ok(fichaAtencionDto);
    }

    @GetMapping
    public ResponseEntity<List<FichaAtencionDto>> getAllFichas() {
        List<FichaAtencionDto> fichas = fichaAtencionService.getAllFichas();
        return ResponseEntity.ok(fichas);
    }

    @GetMapping("/{fichaId}")
public ResponseEntity<FichaAtencionDto> getFichaById(@PathVariable Long fichaId) {
    FichaAtencionDto ficha = fichaAtencionService.getFichaById(fichaId);
    return ResponseEntity.ok(ficha);
}

    

    @GetMapping("/medico/{medicoId}/fecha/{fecha}")
    public ResponseEntity<List<FichaAtencionDto>> getFichasByMedicoAndFecha(
            @PathVariable Long medicoId,
            @PathVariable LocalDate fecha) {
        List<FichaAtencionDto> fichas = fichaAtencionService.getFichasByMedicoAndFecha(medicoId, fecha);
        return ResponseEntity.ok(fichas);
    }
}
