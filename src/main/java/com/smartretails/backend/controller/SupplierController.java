package com.smartretails.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartretails.backend.config.ApiResponse;
import com.smartretails.backend.dto.SupplierRequest;
import com.smartretails.backend.dto.SupplierDto;
import com.smartretails.backend.entity.Supplier;
import com.smartretails.backend.mapper.DtoMapper;
import com.smartretails.backend.service.SupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierDto>> createSupplier(@Valid @RequestBody SupplierRequest req) {
        Supplier supplier = Supplier.builder()
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .address(req.getAddress())
                .contactPerson(req.getContactPerson())
                .isActive(req.getIsActive())
                .build();
        return ResponseEntity.ok(ApiResponse.success("Supplier created",
                DtoMapper.toSupplierDto(supplierService.createSupplier(supplier))));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierDto>>> getAllSuppliers() {
        return ResponseEntity.ok(
                ApiResponse.success("Suppliers fetched successfully", supplierService.getAllSuppliers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierDto>> getSupplierById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiResponse.success(supplierService.getSupplierById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierDto>> updateSupplier(@PathVariable("id") Long id,
            @RequestBody SupplierRequest req) {

        Supplier s = Supplier.builder()
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .address(req.getAddress())
                .contactPerson(req.getContactPerson())
                .isActive(req.getIsActive())
                .build();

        return ResponseEntity
                .ok(ApiResponse.success(supplierService.updateSupplier(id, s)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable("id") Long id) {
        supplierService.deleteSupplier(id);

        return ResponseEntity.noContent().build();
    }
}
