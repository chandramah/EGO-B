package com.smartretails.backend.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.smartretails.backend.dto.LowStockDto;
import com.smartretails.backend.dto.SalesReportDto;
import com.smartretails.backend.entity.Product;
import com.smartretails.backend.repository.ProductRepository;
import com.smartretails.backend.repository.SaleRepository;
import com.smartretails.backend.repository.StockBatchRepository;
import com.smartretails.backend.service.ReportsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final StockBatchRepository stockBatchRepository;

    @Override
    public SalesReportDto salesSummary(LocalDate from, LocalDate to) {

        LocalDateTime startDate = from.atStartOfDay();
        LocalDateTime endDate = to.atTime(LocalTime.MAX);

        List<Object[]> results = saleRepository.aggregateBetween(startDate, endDate);

        if (results.isEmpty()) {
            return SalesReportDto.builder()
                    .from(from.toString())
                    .to(to.toString())
                    .transactions(0)
                    .grossTotal(BigDecimal.ZERO)
                    .discountTotal(BigDecimal.ZERO)
                    .netTotal(BigDecimal.ZERO)
                    .taxTotal(BigDecimal.ZERO)
                    .build();
        }

        Object[] result = results.get(0);

        long transactions = ((Number) result[0]).longValue();
        BigDecimal gross = (BigDecimal) result[1];
        BigDecimal discount = (BigDecimal) result[2];
        BigDecimal net = (BigDecimal) result[3];
        BigDecimal tax = (BigDecimal) result[4];

        return SalesReportDto.builder()
                .from(from.toString())
                .to(to.toString())
                .transactions(transactions)
                .grossTotal(gross)
                .discountTotal(discount)
                .netTotal(net)
                .taxTotal(tax)
                .build();
    }

    @Override
    public List<LowStockDto> lowStock() {

        int page = 0;
        int size = 200;

        List<LowStockDto> list = new ArrayList<>();
        Page<Product> p;

        do {
            p = productRepository.findByIsActiveTrue(PageRequest.of(page++, size));
            for (Product prod : p.getContent()) {
                Integer qty = stockBatchRepository.sumQuantityByProductId(prod.getId());
                int available = qty == null ? 0 : qty;
                if (prod.getReorderLevel() != null && available <= prod.getReorderLevel()) {
                    list.add(LowStockDto.builder()
                            .productId(prod.getId())
                            .sku(prod.getSku())
                            .name(prod.getName())
                            .availableQty(available)
                            .reorderLevel(prod.getReorderLevel())
                            .build());
                }
            }
        } while (!p.isLast());

        return list;
    }

}
