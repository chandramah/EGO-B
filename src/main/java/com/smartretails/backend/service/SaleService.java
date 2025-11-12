package com.smartretails.backend.service;

import com.smartretails.backend.config.PageResponse;
import com.smartretails.backend.dto.SaleDto;
import com.smartretails.backend.dto.SaleRequest;
import com.smartretails.backend.entity.Sale;

public interface SaleService {

    Sale createSale(SaleRequest saleRequest);

    Sale getSaleById(Long id);

    PageResponse<SaleDto> getAllSales(int page, int size);

}
