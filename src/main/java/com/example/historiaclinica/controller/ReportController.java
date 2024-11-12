package com.example.historiaclinica.controller;

import com.example.historiaclinica.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generatePdfReport(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) Long pacienteId,
            @RequestParam(required = false) Long medicoId,
            @RequestParam(required = false) Long especialidadId) throws JRException {
        byte[] pdfReport = reportService.generatePdfReport(startDate, endDate, pacienteId, medicoId, especialidadId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reporte_atencion.pdf");
        return ResponseEntity.ok().headers(headers).body(pdfReport);
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> generateExcelReport(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) Long pacienteId,
            @RequestParam(required = false) Long medicoId,
            @RequestParam(required = false) Long especialidadId) throws Exception {
        byte[] excelReport = reportService.generateExcelReport(startDate, endDate, pacienteId, medicoId, especialidadId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "reporte_atencion.xlsx");
        return ResponseEntity.ok().headers(headers).body(excelReport);
    }
}
