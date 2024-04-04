package com.inditex.prueba.price.application.repositories;

import com.inditex.prueba.price.domain.models.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
    Optional<Price> findPriceDetails(LocalDateTime date, Integer productId, Integer brandId);
}
