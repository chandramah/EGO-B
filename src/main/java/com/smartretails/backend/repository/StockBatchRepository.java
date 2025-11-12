package com.smartretails.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartretails.backend.entity.StockBatch;

@Repository
public interface StockBatchRepository extends JpaRepository<StockBatch, Long> {
    List<StockBatch> findByProduct_Id(Long productId);

    @Query("select coalesce(sum(b.quantity),0) from StockBatch b where b.product.id = :productId")
    Integer sumQuantityByProductId(@Param("productId") Long productId);

    List<StockBatch> findByProduct_IdOrderByExpiryDateAscCreatedAtAsc(Long productId);
}
