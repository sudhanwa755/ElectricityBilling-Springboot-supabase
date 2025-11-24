package com.example.ebs.controller;

import com.example.ebs.service.ReportService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.time.YearMonth;
import java.util.Map;

@Controller @RequestMapping("/reports")
public class ReportsController {
    private final ReportService reports;
    public ReportsController(ReportService reports){ this.reports = reports; }

    @GetMapping
    public String index(Model m){
        Map<YearMonth, Double> revenue = reports.monthlyRevenue(6);
        Map<YearMonth, Integer> usage = reports.monthlyUsage(6);
        m.addAttribute("revenue", revenue);
        m.addAttribute("usage", usage);
        return "reports/index";
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> excel(){
        Map<YearMonth, Double> revenue = reports.monthlyRevenue(12);
        try (Workbook wb = new XSSFWorkbook()){
            Sheet s = wb.createSheet("Revenue");
            int r = 0;
            Row hdr = s.createRow(r++);
            hdr.createCell(0).setCellValue("Month");
            hdr.createCell(1).setCellValue("Revenue");
            for (Map.Entry<YearMonth, Double> e: revenue.entrySet()){
                Row row = s.createRow(r++);
                row.createCell(0).setCellValue(e.getKey().toString());
                row.createCell(1).setCellValue(e.getValue());
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            wb.write(baos);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reports.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(baos.toByteArray());
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
