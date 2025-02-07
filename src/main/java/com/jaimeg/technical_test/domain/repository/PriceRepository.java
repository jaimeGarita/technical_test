package com.jaimeg.technical_test.domain.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jaimeg.technical_test.domain.model.Price;

public interface PriceRepository extends JpaRepository<Price, UUID> {



        @Query("SELECT p FROM Price p " +
                        "WHERE p.productId = :productId " +
                        "  AND p.brand.id = :brandId " +
                        "  AND p.startDate <= :applicationDate " +
                        "  AND p.endDate >= :applicationDate " +
                        "ORDER BY p.priority DESC")
        List<Price> findApplicablePrices(
                        @Param("productId") Integer productId,
                        @Param("brandId") Long brandId,
                        @Param("applicationDate") Timestamp applicationDate);
}