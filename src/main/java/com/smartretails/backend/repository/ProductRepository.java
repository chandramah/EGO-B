package com.smartretails.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartretails.backend.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);

    @Query("SELECT p FROM Product p WHERE (:sku IS NULL OR p.sku LIKE %:sku%) AND (:name IS NULL OR p.name LIKE %:name%)")
    Page<Product> search(@Param("sku") String sku, @Param("name") String name, Pageable pageable);

    Page<Product> findByIsActiveTrue(Pageable pageable);
}
