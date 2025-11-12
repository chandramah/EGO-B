package com.smartretails.backend.service;

import java.util.List;

import com.smartretails.backend.entity.PurchaseOrderItem;

public interface PurchaseOrderItemService {
    PurchaseOrderItem createPurchaseOrderItems(PurchaseOrderItem purchaseOrderItem);
    List<PurchaseOrderItem> getItemsByPurchaseOrder(Long purchaseOrderId);
}
