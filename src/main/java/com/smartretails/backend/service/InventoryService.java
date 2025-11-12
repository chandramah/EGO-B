package com.smartretails.backend.service;

import java.util.List;

import com.smartretails.backend.entity.StockBatch;

public interface InventoryService {
    List<StockBatch> geStockBatchs(Long productId);
    StockBatch addBatch(StockBatch batch);
    int decrementStock(Long productId, int qty);
}
