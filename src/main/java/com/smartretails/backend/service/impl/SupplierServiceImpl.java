package com.smartretails.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smartretails.backend.dto.SupplierDto;
import com.smartretails.backend.entity.Supplier;
import com.smartretails.backend.mapper.DtoMapper;
import com.smartretails.backend.repository.SupplierRepository;
import com.smartretails.backend.service.SupplierService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    @Transactional
    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(DtoMapper::toSupplierDto)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDto getSupplierById(Long id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

        return DtoMapper.toSupplierDto(supplier);
    }

    @Override
    public SupplierDto updateSupplier(Long id, Supplier rqs) {
        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
        existing.setName(rqs.getName());
        existing.setEmail(rqs.getEmail());
        existing.setPhone(rqs.getPhone());
        existing.setAddress(rqs.getAddress());
        existing.setContactPerson(rqs.getContactPerson());
        existing.setIsActive(rqs.getIsActive());

        return DtoMapper.toSupplierDto(supplierRepository.save(existing));

    }

    @Override
    public void deleteSupplier(Long id) {
        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

        supplierRepository.delete(existing);
    }
}
