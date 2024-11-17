// src/main/java/com/tu_proyecto/service/BitacoraService.java
package com.example.historiaclinica.service;

import com.example.historiaclinica.dto.BitacoraDto;
import com.example.historiaclinica.model.Bitacora;
import com.example.historiaclinica.repository.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BitacoraService {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    public BitacoraDto saveAction(String accion) {
        Bitacora bitacora = new Bitacora(accion, LocalDateTime.now());
        bitacoraRepository.save(bitacora);
        return new BitacoraDto(bitacora.getAccion(), bitacora.getFecha());
    }

    public List<BitacoraDto> getAllActions() {
        return bitacoraRepository.findAll().stream()
                .map(bitacora -> new BitacoraDto(bitacora.getAccion(), bitacora.getFecha()))
                .collect(Collectors.toList());
    }
}
