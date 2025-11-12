package com.smartretails.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockBatchDto {
    private Long id;
    private Long productId;
    private String productSku;
    private Integer quantity;
    private BigDecimal costPrice;
    private LocalDate expiryDate;
    private String location;
    private String batchNumber;
    private LocalDateTime createdAt;
}
