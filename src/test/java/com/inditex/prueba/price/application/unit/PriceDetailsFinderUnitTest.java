package com.inditex.prueba.price.application.unit;

import com.inditex.prueba.common.exceptions.InvalidParameterException;
import com.inditex.prueba.price.application.repositories.PriceRepository;
import com.inditex.prueba.price.application.usecases.pricedetailsfinder.PriceDetailsFinderImpl;
import com.inditex.prueba.price.domain.exceptions.PriceNotFoundException;
import com.inditex.prueba.price.domain.models.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceDetailsFinderUnitTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceDetailsFinderImpl priceDetailsFinder;

    @BeforeEach
    void setUp() {

    }

    @Test
    void whenValidParameters_thenFindPriceDetails() {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        Integer productId = 1;
        Integer brandId = 1;
        Price expectedPrice = new Price(
                1,
                brandId,
                date,
                date.plusDays(1),
                productId,
                1,
                0,
                new BigDecimal("29.99"),
                "EUR");

        when(priceRepository.findPriceDetails(date, productId, brandId)).thenReturn(Optional.of(expectedPrice));

        Price result = priceDetailsFinder.run(date, productId, brandId);

        assertNotNull(result);
        assertEquals(expectedPrice, result);
    }

    @Test
    void whenDateIsNull_thenThrowInvalidParameterException() {
        assertThrows(InvalidParameterException.class, () -> priceDetailsFinder.run(null, 1, 1));
    }

    @Test
    void whenDateIsInFuture_thenThrowInvalidParameterException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        assertThrows(InvalidParameterException.class, () -> priceDetailsFinder.run(futureDate, 1, 1));
    }

    @Test
    void whenProductIdIsInvalid_thenThrowInvalidParameterException() {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        assertAll(
                () -> assertThrows(InvalidParameterException.class,
                        () -> priceDetailsFinder.run(date, null, 1)),
                () -> assertThrows(InvalidParameterException.class,
                        () -> priceDetailsFinder.run(date, 0, 1)),
                () -> assertThrows(InvalidParameterException.class,
                        () -> priceDetailsFinder.run(date, -1, 1))
        );
    }

    @Test
    void whenBrandIdIsInvalid_thenThrowInvalidParameterException() {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        assertAll(
                () -> assertThrows(InvalidParameterException.class,
                        () -> priceDetailsFinder.run(date, 1, null)),
                () -> assertThrows(InvalidParameterException.class,
                        () -> priceDetailsFinder.run(date, 1, 0)),
                () -> assertThrows(InvalidParameterException.class,
                        () -> priceDetailsFinder.run(date, 1, -1))
        );
    }

    @Test
    void whenPriceDetailsNotFound_thenThrowPriceNotFoundException() {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        Integer productId = 1;
        Integer brandId = 1;

        when(priceRepository.findPriceDetails(date, productId, brandId)).thenReturn(Optional.empty());

        assertThrows(PriceNotFoundException.class, () -> priceDetailsFinder.run(date, productId, brandId));
    }
}

