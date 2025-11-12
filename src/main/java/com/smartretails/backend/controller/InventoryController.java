package com.smartretails.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartretails.backend.config.ApiResponse;
import com.smartretails.backend.dto.StockBatchDto;
import com.smartretails.backend.dto.StockBatchRequest;
import com.smartretails.backend.entity.Product;
import com.smartretails.backend.entity.StockBatch;
import com.smartretails.backend.exception.ResourceNotFoundException;
import com.smartretails.backend.mapper.DtoMapper;
import com.smartretails.backend.repository.ProductRepository;
import com.smartretails.backend.service.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryRepository;
    private final ProductRepository productRepository;

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<List<StockBatchDto>>> getStock(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(ApiResponse.success(
                DtoMapper.mapList(inventoryRepository.geStockBatchs(productId), DtoMapper::toStockBatchDto)));
    }

    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<StockBatchDto>> addBatch(@Valid @RequestBody StockBatchRequest req) {
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        StockBatch batch = StockBatch.builder()
                .product(product)
                .quantity(req.getQuantity())
                .costPrice(req.getCostPrice())
                .expiryDate(req.getExpiryDate())
                .location(req.getLocation())
                .batchNumber(req.getBatchNumber())
                .build();

        return ResponseEntity.ok(ApiResponse.success("Batch saved",
                DtoMapper.toStockBatchDto(inventoryRepository.addBatch(batch))));
    }

}
