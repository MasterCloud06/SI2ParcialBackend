package com.example.historiaclinica.controller;

import com.example.historiaclinica.model.AuditLog;
import com.example.historiaclinica.service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;


    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    // Endpoint para obtener todos los registros de la bitácora
    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllAuditLogs() {
        List<AuditLog> auditLogs = auditLogService.getAllAuditLogs();
        return ResponseEntity.ok(auditLogs);
    }

    // Endpoint para obtener registros por nombre de usuario
    @GetMapping("/user/{username}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByUsername(@PathVariable String username) {
        List<AuditLog> auditLogs = auditLogService.getAuditLogsByUsername(username);
        return ResponseEntity.ok(auditLogs);
    }

    // Endpoint para obtener registros por acción específica
    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByAction(@PathVariable String action) {
        List<AuditLog> auditLogs = auditLogService.getAuditLogsByAction(action);
        return ResponseEntity.ok(auditLogs);
    }
}
