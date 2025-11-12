package com.smartretails.backend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderRequest {
    @NotNull
    private Long supplierId;
    private LocalDate expectedDate;
    @NotNull
    private String status; // PENDING, APPROVED, RECEIVED, CANCELLED
    private String orderNumber;
    private String notes;
}
