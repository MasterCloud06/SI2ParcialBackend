package com.example.historiaclinica.service;

import com.example.historiaclinica.model.AuditLog;
import com.example.historiaclinica.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void logAction(String action, String username, String ipAddress, String details) {
        AuditLog auditLog = new AuditLog(action, username, ipAddress, details);
        auditLogRepository.save(auditLog);
    }

    // Método para obtener todos los registros de bitácora
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }

    // Método para obtener registros de bitácora por nombre de usuario
    public List<AuditLog> getAuditLogsByUsername(String username) {
        return auditLogRepository.findByUsername(username);
    }

    // Método para obtener registros de bitácora por acción
    public List<AuditLog> getAuditLogsByAction(String action) {
        return auditLogRepository.findByAction(action);
    }
}
