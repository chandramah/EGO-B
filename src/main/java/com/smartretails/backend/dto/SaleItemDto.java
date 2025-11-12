package com.smartretails.backend.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleItemDto {
    
    private Long id;
    private Long productId;

    private Integer quantity;

    private String productName;

    private String productSku;

    private BigDecimal unitPrice;

    private BigDecimal taxRate;

}
