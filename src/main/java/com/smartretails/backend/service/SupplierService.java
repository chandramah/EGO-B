package com.smartretails.backend.service;

import java.util.List;

import com.smartretails.backend.dto.SupplierDto;
import com.smartretails.backend.entity.Supplier;

public interface SupplierService {
    Supplier createSupplier(Supplier supplier);

    List<SupplierDto> getAllSuppliers();

    SupplierDto getSupplierById(Long id);

    SupplierDto updateSupplier(Long id, Supplier req);

    void deleteSupplier(Long id);
}
