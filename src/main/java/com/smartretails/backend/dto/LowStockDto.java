package com.smartretails.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LowStockDto {
    private Long productId;
    private String sku;
    private String name;
    private Integer availableQty;
    private Integer reorderLevel;
}   
