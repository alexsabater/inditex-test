package com.inditex.prueba.price.application.usecases.pricedetailsfinder;

import com.inditex.prueba.price.domain.models.Price;

import java.time.LocalDateTime;

public interface PriceDetailsFinder {
    Price run(LocalDateTime date, Integer productId, Integer brandId);
}
