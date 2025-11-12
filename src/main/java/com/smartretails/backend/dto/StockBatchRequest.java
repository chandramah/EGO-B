package com.smartretails.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockBatchRequest {
    @NotNull
    private Long productId;
    @NotNull @Min(0)
    private Integer quantity;
    @NotNull
    private BigDecimal costPrice;
    private LocalDate expiryDate;
    @NotBlank
    private String location;
    private String batchNumber;
}
