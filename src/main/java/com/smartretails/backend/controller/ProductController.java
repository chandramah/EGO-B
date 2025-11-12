package com.smartretails.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartretails.backend.config.ApiResponse;
import com.smartretails.backend.config.PageResponse;
import com.smartretails.backend.dto.ProductCreateRequest;
import com.smartretails.backend.dto.ProductDto;
import com.smartretails.backend.dto.ProductUpdateRequest;
import com.smartretails.backend.entity.Product;
import com.smartretails.backend.mapper.DtoMapper;
import com.smartretails.backend.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductDto>>> getProducts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<ProductDto> resp = productRepository.getProducts(page, size);
        return ResponseEntity.ok(ApiResponse.success(resp));
    }

    @GetMapping("/search-products")
    public ResponseEntity<ApiResponse<PageResponse<ProductDto>>> search(@RequestParam(required = false) String sku,
            @RequestParam(required = false) String name, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<ProductDto> resp = productRepository.searchProduct(sku, name, page, size);
        return ResponseEntity.ok(ApiResponse.success(resp));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@Valid @RequestBody ProductCreateRequest req) {
        Product product = Product.builder()
                .name(req.getName())
                .sku(req.getSku())
                .category(req.getCategory())
                .barcode(req.getBarcode())
                .isActive(req.getIsActive())
                .reorderLevel(req.getReorderLevel())
                .description(req.getDescription())
                .unitPrice(req.getUnitPrice())
                .taxRate(req.getTaxRate())
                .build();
        Product createdProduct = productRepository.createProduct(product);
        return ResponseEntity.ok(ApiResponse.success("created", DtoMapper.toProductDto(createdProduct)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable("id") long id,
            @Valid @RequestBody ProductUpdateRequest req) {
        Product product = Product.builder()
                .name(req.getName())
                .sku(req.getSku())
                .category(req.getCategory())
                .barcode(req.getBarcode())
                .isActive(req.getIsActive())
                .reorderLevel(req.getReorderLevel())
                .description(req.getDescription())
                .unitPrice(req.getUnitPrice())
                .taxRate(req.getTaxRate())
                .build();
        Product updatedProduct = productRepository.updateProduct(id, product);
        return ResponseEntity.ok(ApiResponse.success("Updated", DtoMapper.toProductDto(updatedProduct)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiResponse.success(DtoMapper.toProductDto(productRepository.getProductById(id))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
