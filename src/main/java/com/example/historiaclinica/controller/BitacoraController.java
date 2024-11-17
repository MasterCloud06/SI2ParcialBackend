package com.example.historiaclinica.controller;

import com.example.historiaclinica.dto.BitacoraDto;
import com.example.historiaclinica.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bitacora")
@CrossOrigin(origins = "*") // Permite que el frontend acceda a estos endpoints
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    @PostMapping("/registrar")
    public BitacoraDto registrarAccion(@RequestBody String accion) {
        return bitacoraService.saveAction(accion);
    }

    @GetMapping("/todas")
    public List<BitacoraDto> obtenerAcciones() {
        return bitacoraService.getAllActions();
    }   
}