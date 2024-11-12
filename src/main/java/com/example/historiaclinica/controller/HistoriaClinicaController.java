package com.example.historiaclinica.controller;

import com.example.historiaclinica.dto.HistoriaClinicaDto;
import com.example.historiaclinica.dto.HistoriaClinicaRequestDto;
import com.example.historiaclinica.service.HistoriaClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiaclinica")
public class HistoriaClinicaController {

    @Autowired
    private HistoriaClinicaService historiaClinicaService;

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistoriaClinicaDto>> getHistoriaByPaciente(@PathVariable Long pacienteId) {
        List<HistoriaClinicaDto> historiaClinica = historiaClinicaService.getHistoriaClinicaByPaciente(pacienteId);
        return ResponseEntity.ok(historiaClinica);
    }

    @PostMapping
    public ResponseEntity<HistoriaClinicaDto> createHistoriaClinica(@RequestBody HistoriaClinicaRequestDto requestDto) {
        HistoriaClinicaDto historiaClinica = historiaClinicaService.createHistoriaClinica(requestDto);
        return ResponseEntity.ok(historiaClinica);
    }
}
