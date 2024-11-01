package com.example.salesapp.controller;

import com.example.salesapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        // Generate PDF using iText
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("Transaction Report"));
        // Add more content here
        document.close();
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // Generate Excel using Apache POI
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transactions");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Transaction ID");
        // Add more header cells here

        // Populate data
        // ...

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}