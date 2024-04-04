package com.inditex.prueba.price.infrastructure.persistence.repositories;

import com.inditex.prueba.price.application.repositories.PriceRepository;
import com.inditex.prueba.price.domain.models.Price;
import com.inditex.prueba.price.infrastructure.persistence.entities.PriceJpaEntity;
import com.inditex.prueba.price.infrastructure.persistence.mappers.PriceJpaMapper;
import com.inditex.prueba.price.infrastructure.persistence.repositories.jpa.PriceJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;
    public PriceRepositoryImpl (PriceJpaRepository priceJpaRepository) {
        this.priceJpaRepository = priceJpaRepository;
    }
    private static final PriceJpaMapper priceJpaMapper = PriceJpaMapper.INSTANCE;
    @Override
    public Optional<Price> findPriceDetails(LocalDateTime date, Integer productId, Integer brandId) {
        Optional<PriceJpaEntity> optionalPriceEntity = priceJpaRepository.findPriceDetails(date, productId, brandId);
        return optionalPriceEntity.map(priceJpaMapper::jpaToDomain);
    }
}
