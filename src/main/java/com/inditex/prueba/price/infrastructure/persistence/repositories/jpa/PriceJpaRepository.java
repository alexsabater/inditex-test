package com.inditex.prueba.price.infrastructure.persistence.repositories.jpa;

import com.inditex.prueba.price.infrastructure.persistence.entities.PriceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceJpaRepository extends JpaRepository<PriceJpaEntity, Long> {
    @Query(value =
            "SELECT * FROM PRICES p " +
                    "WHERE p.PRODUCT_ID = :productId " +
                    "AND p.BRAND_ID = :brandId " +
                    "AND p.START_DATE <= :date " +
                    "AND p.END_DATE >= :date " +
                    "ORDER BY p.PRIORITY DESC LIMIT 1", nativeQuery = true)
    Optional<PriceJpaEntity> findPriceDetails(
            @Param("date") LocalDateTime date,
            @Param("productId") Integer productId,
            @Param("brandId") Integer brandId);
}
