package com.smartretails.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartretails.backend.entity.PurchaseOrder;
import com.smartretails.backend.repository.PurchaseOrderRepository;
import com.smartretails.backend.service.PurchaseOrderService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public List<PurchaseOrder> getAllOrders() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    @Transactional
    public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public PurchaseOrder getOrderById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchase order not found"));
    }

    @Override
    @Transactional
    public PurchaseOrder updateOrder(Long id, PurchaseOrder purchaseOrder) {
        PurchaseOrder existing = getOrderById(id);
        existing.setSupplier(purchaseOrder.getSupplier());
        existing.setExpectedDate(purchaseOrder.getExpectedDate());
        existing.setStatus(purchaseOrder.getStatus());
        existing.setOrderNumber(purchaseOrder.getOrderNumber());
        existing.setNotes(purchaseOrder.getNotes());
        return purchaseOrderRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }
}
