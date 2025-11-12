package com.smartretails.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String sku;
    private String category;
    private String barcode;
    private Boolean isActive;
    private Integer reorderLevel;
    private String description;
    private BigDecimal unitPrice;
    private BigDecimal taxRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
