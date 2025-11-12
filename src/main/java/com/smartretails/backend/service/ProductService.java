package com.smartretails.backend.service;

import com.smartretails.backend.config.PageResponse;
import com.smartretails.backend.dto.ProductDto;
import com.smartretails.backend.entity.Product;

public interface ProductService {
    PageResponse<ProductDto> getProducts(int page, int size);

    PageResponse<ProductDto> searchProduct(String sku, String name, int page, int size);

    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);

    Product getProductById(Long id);

    void deleteProduct(Long id);

}
