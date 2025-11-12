package com.smartretails.backend.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smartretails.backend.config.PageResponse;
import com.smartretails.backend.dto.SaleDto;
import com.smartretails.backend.dto.SaleItemRequest;
import com.smartretails.backend.dto.SaleRequest;
import com.smartretails.backend.entity.Product;
import com.smartretails.backend.entity.Sale;
import com.smartretails.backend.entity.SaleItem;
import com.smartretails.backend.mapper.DtoMapper;
import com.smartretails.backend.repository.ProductRepository;
import com.smartretails.backend.repository.SaleItemRepository;
import com.smartretails.backend.repository.SaleRepository;
import com.smartretails.backend.service.InventoryService;
import com.smartretails.backend.service.SaleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeleServiceImpl implements SaleService {

        private final SaleRepository saleRepository;
        private final ProductRepository productRepository;
        private final SaleItemRepository saleItemRepository;
        private final InventoryService inventoryService;

        @Override
        @Transactional
        public Sale createSale(SaleRequest saleRequest) {

                BigDecimal total = BigDecimal.ZERO;
                BigDecimal taxTotal = BigDecimal.ZERO;
                BigDecimal discountTotal = saleRequest.getDiscountTotal() != null ? saleRequest.getDiscountTotal()
                                : BigDecimal.ZERO;

                Sale sale = Sale.builder()
                                .cashierId(saleRequest.getCashierId())
                                .discountTotal(discountTotal)
                                .total(total)
                                .taxTotal(taxTotal)
                                .paymentMode(Sale.PaymentMode.valueOf(saleRequest.getPaymentMode()))
                                .build();

                List<SaleItem> items = new ArrayList<>();
                for (SaleItemRequest it : saleRequest.getItems()) {
                        Product product = productRepository.findById(it.getProductId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Product not found with id: " + it.getProductId()));
                        // decrement stock first to fail early if insufficient

                        inventoryService.decrementStock(product.getId(), it.getQuantity());

                        BigDecimal unitPrice = it.getUnitPrice() != null ? it.getUnitPrice() : product.getUnitPrice();
                        BigDecimal taxRate = it.getTaxRate() != null ? it.getTaxRate() : product.getTaxRate();

                        BigDecimal lineSubtotal = unitPrice.multiply(BigDecimal.valueOf(it.getQuantity()));
                        BigDecimal lineTax = lineSubtotal.multiply(taxRate).movePointLeft(2);

                        total = total.add(lineSubtotal).add(lineTax);
                        taxTotal = taxTotal.add(lineTax);

                        SaleItem si = SaleItem.builder()
                                        .sale(sale)
                                        .product(product)
                                        .quantity(it.getQuantity())
                                        .unitPrice(unitPrice)
                                        .taxRate(taxRate)
                                        .build();
                        items.add(si);
                }

                sale.setItems(items);
                sale.setTaxTotal(taxTotal);
                sale.setTotal(total.subtract(discountTotal));

                Sale savedSale = saleRepository.save(sale);
                saleItemRepository.saveAll(items);
                return savedSale;
        }

        @Override
        public Sale getSaleById(Long id) {

                return saleRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Sale not found with id: " + id));

        }

        @Override
        public PageResponse<SaleDto> getAllSales(int page, int size) {
                Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
                Page<Sale> salePage = saleRepository.findAll(pageable);

                return PageResponse.<SaleDto>builder()
                                .content(salePage.getContent().stream().map(DtoMapper::toSaleDto).toList())
                                .page(salePage.getNumber())
                                .size(salePage.getSize())
                                .totalElements(salePage.getTotalElements())
                                .totalPages(salePage.getTotalPages())
                                .first(salePage.isFirst())
                                .last(salePage.isLast())
                                .empty(salePage.isEmpty())
                                .build();
        }

}
