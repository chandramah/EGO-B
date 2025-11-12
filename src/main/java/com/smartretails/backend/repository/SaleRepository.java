package com.smartretails.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartretails.backend.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("""
                SELECT
                    COUNT(s),
                    COALESCE(SUM(s.total + s.discountTotal), 0),
                    COALESCE(SUM(s.discountTotal), 0),
                    COALESCE(SUM(s.total), 0),
                    COALESCE(SUM(s.taxTotal), 0)
                FROM Sale s
                WHERE s.createdAt BETWEEN :from AND :to
            """)
    List<Object[]> aggregateBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

}
