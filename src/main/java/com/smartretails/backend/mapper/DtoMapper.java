package com.smartretails.backend.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.smartretails.backend.dto.ProductDto;
import com.smartretails.backend.dto.PurchaseOrderDto;
import com.smartretails.backend.dto.PurchaseOrderItemDto;
import com.smartretails.backend.dto.SaleDto;
import com.smartretails.backend.dto.SaleItemDto;
import com.smartretails.backend.dto.StockBatchDto;
import com.smartretails.backend.dto.SupplierDto;
import com.smartretails.backend.entity.Product;
import com.smartretails.backend.entity.PurchaseOrder;
import com.smartretails.backend.entity.PurchaseOrderItem;
import com.smartretails.backend.entity.Sale;
import com.smartretails.backend.entity.SaleItem;
import com.smartretails.backend.entity.StockBatch;
import com.smartretails.backend.entity.Supplier;

public final class DtoMapper {

    private DtoMapper() {}

    public static ProductDto toProductDto(Product p) {
        if (p == null) return null;
        return ProductDto.builder()
                .id(p.getId())
                .name(p.getName())
                .sku(p.getSku())
                .category(p.getCategory())
                .barcode(p.getBarcode())
                .isActive(p.getIsActive())
                .reorderLevel(p.getReorderLevel())
                .description(p.getDescription())
                .unitPrice(p.getUnitPrice())
                .taxRate(p.getTaxRate())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }

    public static SaleItemDto toSaleItemDto(SaleItem i) {
        if(i == null) return null;
        Product p = i.getProduct();
        return SaleItemDto.builder()
                .id(i.getId())
                .productId(p != null ? p.getId() : null)
                .quantity(i.getQuantity())
                .productName(p != null ? p.getName() : null)
                .productSku(p != null ? p.getSku() : null)
                .unitPrice(i.getUnitPrice())
                .taxRate(i.getTaxRate())
                .build();
                
    }


    public static SaleDto toSaleDto(Sale s) {
        if(s == null) return null;
        return SaleDto.builder()
                .id(s.getId())
                .cashierId(s.getCashierId())
                .total(s.getTotal())
                .taxTotal(s.getTaxTotal())
                .discountTotal(s.getDiscountTotal())
                .paymentMode(s.getPaymentMode() != null ? s.getPaymentMode().name()   : null)
                .createdAt(s.getCreatedAt())
                .items(mapList(s.getItems(), DtoMapper::toSaleItemDto))
                .build();
    }

    public static SupplierDto toSupplierDto(Supplier s) {
        if (s == null) return null;
        return SupplierDto.builder()
                .id(s.getId())
                .name(s.getName())
                .email(s.getEmail())
                .phone(s.getPhone())
                .address(s.getAddress())
                .contactPerson(s.getContactPerson())
                .isActive(s.getIsActive())
                .createdAt(s.getCreatedAt())
                .updatedAt(s.getUpdatedAt())
                .build();
    }

    public static StockBatchDto toStockBatchDto(StockBatch b) {
        if (b == null) return null;
        Product product = b.getProduct();
        return StockBatchDto.builder()
                .id(b.getId())
                .productId(product != null ? product.getId() : null)
                .productSku(product != null ? product.getSku() : null)
                .quantity(b.getQuantity())
                .costPrice(b.getCostPrice())
                .expiryDate(b.getExpiryDate())
                .location(b.getLocation())
                .batchNumber(b.getBatchNumber())
                .createdAt(b.getCreatedAt())
                .build();
    }

    public static PurchaseOrderDto toPurchaseOrderDto(PurchaseOrder po) {
        if (po == null) return null;
        Supplier s = po.getSupplier();
        return PurchaseOrderDto.builder()
                .id(po.getId())
                .supplierId(s != null ? s.getId() : null)
                .supplierName(s != null ? s.getName() : null)
                .expectedDate(po.getExpectedDate())
                .status(po.getStatus() != null ? po.getStatus().name() : null)
                .orderNumber(po.getOrderNumber())
                .notes(po.getNotes())
                .createdAt(po.getCreatedAt())
                .updatedAt(po.getUpdatedAt())
                .build();
    }

    public static PurchaseOrderItemDto toPurchaseOrderItemDto(PurchaseOrderItem i) {
        if (i == null) return null;
        Product p = i.getProduct();
        PurchaseOrder po = i.getPurchaseOrder();
        return PurchaseOrderItemDto.builder()
                .id(i.getId())
                .purchaseOrderId(po != null ? po.getId() : null)
                .productId(p != null ? p.getId() : null)
                .productSku(p != null ? p.getSku() : null)
                .quantity(i.getQuantity())
                .costPrice(i.getCostPrice())
                .receivedQuantity(i.getReceivedQuantity())
                .build();
    }

    public static <T, R> List<R> mapList(List<T> items, java.util.function.Function<T, R> mapper) {
        return items == null ? List.of() : items.stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }
}
