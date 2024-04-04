package com.inditex.prueba.price.application.usecases.pricedetailsfinder;

import com.inditex.prueba.common.exceptions.InvalidParameterException;
import com.inditex.prueba.price.application.repositories.PriceRepository;
import com.inditex.prueba.price.domain.exceptions.PriceNotFoundException;
import com.inditex.prueba.price.domain.models.Price;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Transactional
public class PriceDetailsFinderImpl implements PriceDetailsFinder {
    private final PriceRepository priceRepository;

    public PriceDetailsFinderImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price run(LocalDateTime date, Integer productId, Integer brandId) {
        validateParameters(date, productId, brandId);
        return priceRepository.findPriceDetails(date, productId, brandId)
                .orElseThrow(PriceNotFoundException::new);
    }

    private void validateParameters(LocalDateTime date, Integer productId, Integer brandId) {
        if (date == null || date.isAfter(LocalDateTime.now())) {
            throw new InvalidParameterException();
        }
        if (productId == null || productId <= 0) {
            throw new InvalidParameterException();
        }
        if (brandId == null || brandId <= 0) {
            throw new InvalidParameterException();
        }
    }
}
