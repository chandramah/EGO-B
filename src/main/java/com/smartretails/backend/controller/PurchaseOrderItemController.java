package com.smartretails.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartretails.backend.config.ApiResponse;
import com.smartretails.backend.dto.PurchaseOrderItemRequest;
import com.smartretails.backend.dto.PurchaseOrderItemDto;
import com.smartretails.backend.entity.Product;
import com.smartretails.backend.entity.PurchaseOrder;
import com.smartretails.backend.entity.PurchaseOrderItem;
import com.smartretails.backend.exception.ResourceNotFoundException;
import com.smartretails.backend.mapper.DtoMapper;
import com.smartretails.backend.repository.ProductRepository;
import com.smartretails.backend.repository.PurchaseOrderRepository;
import com.smartretails.backend.service.PurchaseOrderItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/purchase-order-items")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PurchaseOrderItemController {

    private final PurchaseOrderItemService purchaseOrderItemRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseOrderItemDto>> createPurchaseOrderItem(
            @Valid @RequestBody PurchaseOrderItemRequest req) {
        PurchaseOrder po = purchaseOrderRepository.findById(req.getPurchaseOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found"));
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        PurchaseOrderItem purchaseOrderItem = PurchaseOrderItem.builder()
                .purchaseOrder(po)
                .product(product)
                .quantity(req.getQuantity())
                .costPrice(req.getCostPrice())
                .receivedQuantity(req.getReceivedQuantity())
                .build();

        return ResponseEntity.ok(ApiResponse.success("Purchase order item saved..",
                DtoMapper.toPurchaseOrderItemDto(
                        purchaseOrderItemRepository.createPurchaseOrderItems(purchaseOrderItem))));
    }

    @GetMapping("/purchase-order/{purchaseOrderId}")
    public ResponseEntity<ApiResponse<List<PurchaseOrderItemDto>>> getItemsByPurchaseOrder(
            @PathVariable("purchaseOrderId") Long purchaseOrderId) {
        return ResponseEntity
                .ok(ApiResponse.success(DtoMapper.mapList(
                        purchaseOrderItemRepository.getItemsByPurchaseOrder(purchaseOrderId),
                        DtoMapper::toPurchaseOrderItemDto)));
    }

}
