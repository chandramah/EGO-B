package com.smartretails.backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderItemRequest {
    @NotNull
    private Long purchaseOrderId;
    @NotNull
    private Long productId;
    @NotNull @Min(1)
    private Integer quantity;
    @NotNull
    private BigDecimal costPrice;
    private Integer receivedQuantity;
}
