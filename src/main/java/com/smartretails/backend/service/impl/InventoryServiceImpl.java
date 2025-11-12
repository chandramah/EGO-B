package com.smartretails.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartretails.backend.entity.StockBatch;
import com.smartretails.backend.repository.StockBatchRepository;
import com.smartretails.backend.service.InventoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final StockBatchRepository stockBatchRepository;

    @Override
    public List<StockBatch> geStockBatchs(Long productId) {
        return stockBatchRepository.findByProduct_Id(productId);
    }

    @Override
    @Transactional
    public StockBatch addBatch(StockBatch batch) {
        return stockBatchRepository.save(batch);
    }

    @Override
    @Transactional
    public int decrementStock(Long productId, int qty) {

        if (qty <= 0)
            return 0;

        Integer available = stockBatchRepository.sumQuantityByProductId(productId);
        if (available == null)
            available = 0;
        if (available < qty) {
            throw new IllegalArgumentException("Insufficient stock  for product " + productId);
        }

        int remaining = qty;
        List<StockBatch> fifoBatches = stockBatchRepository.findByProduct_IdOrderByExpiryDateAscCreatedAtAsc(productId);

        for (StockBatch b : fifoBatches) {
            if (remaining == 0)
                break;
            int take = Math.min(b.getQuantity(), remaining);
            b.setQuantity(b.getQuantity() - take);
            remaining -= take;
            if (b.getQuantity() == 0) {
                stockBatchRepository.delete(b);
            } else {
                stockBatchRepository.save(b);
            }
        }
        return qty;
    }

}
