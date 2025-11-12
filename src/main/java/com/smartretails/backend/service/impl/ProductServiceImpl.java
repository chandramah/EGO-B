package com.smartretails.backend.service.impl;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smartretails.backend.config.PageResponse;
import com.smartretails.backend.dto.ProductDto;
import com.smartretails.backend.entity.Product;
import com.smartretails.backend.mapper.DtoMapper;
import com.smartretails.backend.repository.ProductRepository;
import com.smartretails.backend.repository.PurchaseOrderItemRepository;
import com.smartretails.backend.repository.SaleItemRepository;
import com.smartretails.backend.repository.StockBatchRepository;
import com.smartretails.backend.service.ProductService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productJpaRepository;
    private final StockBatchRepository stockBatchRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final SaleItemRepository saleItemRepository;

    public PageResponse<ProductDto> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Product> productPage = productJpaRepository.findAll(pageable);

        return buildPageResponse(productPage);
    }

    public PageResponse<ProductDto> searchProduct(String sku, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> productPage = productJpaRepository.search(sku, name, pageable);

        return buildPageResponse(productPage);
    }

    private PageResponse<ProductDto> buildPageResponse(Page<Product> productPage) {
        return PageResponse.<ProductDto>builder()
                .content(productPage.getContent().stream().map(DtoMapper::toProductDto).collect(Collectors.toList()))
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .first(productPage.isFirst())
                .last(productPage.isLast())
                .empty(productPage.isEmpty())
                .build();
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        if (productJpaRepository.existsBySku(product.getSku())) {
            throw new ValidationException("Sku must be unique..");
        }

        return productJpaRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = productJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found!!"));

        if (!existingProduct.getSku().equals(product.getSku()) && productJpaRepository.existsBySku(product.getSku())) {
            throw new ValidationException("Sku must be unique..");
        }

        existingProduct.setSku(product.getSku());
        existingProduct.setBarcode(product.getBarcode());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setIsActive(product.getIsActive());
        existingProduct.setName(product.getName());
        existingProduct.setUnitPrice(product.getUnitPrice());
        existingProduct.setTaxRate(product.getTaxRate());
        existingProduct.setReorderLevel(product.getReorderLevel());
        return productJpaRepository.save(existingProduct);
    }

    @Override
    public Product getProductById(Long id) {
        return productJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found!!"));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);

        boolean hasStock = !stockBatchRepository.findByProduct_Id(id).isEmpty();
        boolean usedInPoItems = purchaseOrderItemRepository.existsByProduct_Id(id);
        boolean usedInSaleItems = saleItemRepository.existsByProductId(id);

        if (hasStock || usedInPoItems || usedInSaleItems) {
            throw new ValidationException("Cannot delete product with references in stcok, purchase orders, or sales");
        }

        productJpaRepository.delete(product);
    }

}
