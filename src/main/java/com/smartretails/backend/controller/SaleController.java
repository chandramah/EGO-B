package com.smartretails.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartretails.backend.config.ApiResponse;
import com.smartretails.backend.config.PageResponse;
import com.smartretails.backend.dto.SaleDto;
import com.smartretails.backend.dto.SaleRequest;
import com.smartretails.backend.mapper.DtoMapper;
import com.smartretails.backend.service.SaleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @RequestMapping("/create")
    public ResponseEntity<ApiResponse<SaleDto>> createSale(@Valid @RequestBody SaleRequest saleRequest) {
        return ResponseEntity.ok(ApiResponse.success(DtoMapper.toSaleDto(saleService.createSale(saleRequest))));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<SaleDto>>> getAllSales(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(saleService.getAllSales(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleDto>> getSaleById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiResponse.success(DtoMapper.toSaleDto(saleService.getSaleById(id))));
    }
}
