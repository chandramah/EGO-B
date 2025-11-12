package com.smartretails.backend.dto;

import java.math.BigDecimal;

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
public class ProductCreateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String sku;
    @NotBlank
    private String category;
    private String barcode;
    @NotNull
    private Boolean isActive;
    @NotNull @Min(0)
    private Integer reorderLevel;
    private String description;
    @NotNull
    private BigDecimal unitPrice;
    @NotNull
    private BigDecimal taxRate;
}
