package com.smartretails.backend.service;

import java.util.List;

import com.smartretails.backend.entity.PurchaseOrder;

public interface PurchaseOrderService {
    List<PurchaseOrder> getAllOrders();
    PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder);
    PurchaseOrder getOrderById(Long id);
    PurchaseOrder updateOrder(Long id, PurchaseOrder purchaseOrder);
    void deleteOrder(Long id);
}
