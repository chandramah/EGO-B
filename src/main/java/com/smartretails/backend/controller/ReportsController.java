package com.smartretails.backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartretails.backend.config.ApiResponse;
import com.smartretails.backend.dto.LowStockDto;
import com.smartretails.backend.dto.SalesReportDto;
import com.smartretails.backend.service.ReportsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;

    @GetMapping("/sales")
    public ResponseEntity<ApiResponse<SalesReportDto>> getSalesReport(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate to) {
        return ResponseEntity.ok(ApiResponse.success(reportsService.salesSummary(from, to)));

    }

    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<LowStockDto>>> lowStock() {
        return ResponseEntity.ok(ApiResponse.success(reportsService.lowStock()));
    }
}
