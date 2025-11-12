package com.smartretails.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SaleDto {

    private Long id;

    private Long cashierId;

    private BigDecimal total;

    private BigDecimal taxTotal;
    
    private BigDecimal discountTotal;

    private String paymentMode;

    private LocalDateTime createdAt;

    private List<SaleItemDto> items;


}
