package com.smartretails.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartretails.backend.entity.PurchaseOrderItem;
import com.smartretails.backend.repository.PurchaseOrderItemRepository;
import com.smartretails.backend.service.PurchaseOrderItemService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseOrderItemServiceImpl implements PurchaseOrderItemService {

    private final PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Override
    @Transactional
    public PurchaseOrderItem createPurchaseOrderItems(PurchaseOrderItem purchaseOrderItem) {
        return purchaseOrderItemRepository.save(purchaseOrderItem);
    }

    @Override
    public List<PurchaseOrderItem> getItemsByPurchaseOrder(Long purchaseOrderId) {
        return purchaseOrderItemRepository.findByPurchaseOrder_Id(purchaseOrderId);
    }
}
