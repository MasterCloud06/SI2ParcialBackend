package com.example.historiaclinica.service;

import com.example.historiaclinica.model.FichaAtencion;
import com.example.historiaclinica.repository.FichaAtencionRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private FichaAtencionRepository fichaAtencionRepository;

    public byte[] generatePdfReport(LocalDate startDate, LocalDate endDate, Long pacienteId, Long medicoId, Long especialidadId) throws JRException {
        List<FichaAtencion> fichas = fetchFichas(startDate, endDate, pacienteId, medicoId, especialidadId);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fichas);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Sistema de Historias Clínicas");

        InputStream reportStream = getClass().getResourceAsStream("/reports/atencion_report_template.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }

    public byte[] generateExcelReport(LocalDate startDate, LocalDate endDate, Long pacienteId, Long medicoId, Long especialidadId) throws Exception {
        List<FichaAtencion> fichas = fetchFichas(startDate, endDate, pacienteId, medicoId, especialidadId);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte de Atención");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Paciente");
        headerRow.createCell(2).setCellValue("Médico");
        headerRow.createCell(3).setCellValue("Especialidad");
        headerRow.createCell(4).setCellValue("Fecha");
        headerRow.createCell(5).setCellValue("Hora");
        headerRow.createCell(6).setCellValue("Estado");

        int rowNum = 1;
        for (FichaAtencion ficha : fichas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ficha.getId());
            row.createCell(1).setCellValue(ficha.getPaciente().getNombre());
            row.createCell(2).setCellValue(ficha.getMedico().getNombre());
            row.createCell(3).setCellValue(ficha.getEspecialidad().getNombre());
            row.createCell(4).setCellValue(ficha.getFecha().toString());
            row.createCell(5).setCellValue(ficha.getHora().toString());
            row.createCell(6).setCellValue(ficha.getEstado());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    private List<FichaAtencion> fetchFichas(LocalDate startDate, LocalDate endDate, Long pacienteId, Long medicoId, Long especialidadId) {
        // Cargar todas las fichas y aplicar filtros programáticamente
        return fichaAtencionRepository.findAll().stream()
                .filter(f -> startDate == null || !f.getFecha().isBefore(startDate))
                .filter(f -> endDate == null || !f.getFecha().isAfter(endDate))
                .filter(f -> pacienteId == null || f.getPaciente().getId().equals(pacienteId))
                .filter(f -> medicoId == null || f.getMedico().getId().equals(medicoId))
                .filter(f -> especialidadId == null || f.getEspecialidad().getIdEspecialidad().equals(especialidadId))
                .collect(Collectors.toList());
    }
}
