package com.smartretails.backend.service;

import java.time.LocalDate;
import java.util.List;

import com.smartretails.backend.dto.LowStockDto;
import com.smartretails.backend.dto.SalesReportDto;

public interface ReportsService {

    SalesReportDto salesSummary(LocalDate from, LocalDate to);

    List<LowStockDto> lowStock();
}
