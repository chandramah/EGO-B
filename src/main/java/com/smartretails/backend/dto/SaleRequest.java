package com.smartretails.backend.dto;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.List;

import com.smartretails.backend.entity.SaleItem;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleRequest {

    @NotNull
    private Long cashierId;

    @Builder.Default
    private BigDecimal discountTotal = BigDecimal.ZERO;

    @NotNull
    private String paymentMode;

    @NotEmpty
    @Valid
    private List<SaleItemRequest> items;

}
