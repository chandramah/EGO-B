package com.smartretails.backend.dto;

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
public class SupplierRequest {
    @NotBlank
    private String name;
    private String email;
    private String phone;
    private String address;
    private String contactPerson;
    @NotNull
    private Boolean isActive;
}
