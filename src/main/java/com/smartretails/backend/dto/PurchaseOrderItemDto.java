package com.smartretails.backend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderItemDto {
    private Long id;
    private Long purchaseOrderId;
    private Long productId;
    private String productSku;
    private Integer quantity;
    private BigDecimal costPrice;
    private Integer receivedQuantity;
}
