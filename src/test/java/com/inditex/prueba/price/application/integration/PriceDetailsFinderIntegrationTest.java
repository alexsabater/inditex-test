package com.inditex.prueba.price.application.integration;

import com.inditex.prueba.price.application.usecases.pricedetailsfinder.PriceDetailsFinderImpl;
import com.inditex.prueba.price.domain.models.Price;
import com.inditex.prueba.price.infrastructure.persistence.repositories.PriceRepositoryImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PriceDetailsFinderIntegrationTest {

    @InjectMocks
    private PriceDetailsFinderImpl priceDetailsFinder;

    @Mock
    private PriceRepositoryImpl priceRepository;

    private static Stream<PriceTestParams> priceProvider() {
        return Stream.of(
                //Test 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)
                new PriceTestParams(
                        LocalDateTime.of(2020, 6, 14, 10, 0),
                        new Price(
                                1,
                                1,
                                LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                                35455,
                                1,
                                0,
                                new BigDecimal("35.50"),
                                "EUR"
                        )
                ),
                //Test 2: peticiónn a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
                new PriceTestParams(
                        LocalDateTime.of(2020, 6, 14, 16, 0),
                        new Price(
                                2,
                                1,
                                LocalDateTime.of(2020, 6, 14, 15, 0),
                                LocalDateTime.of(2020, 6, 14, 18, 30),
                                35455,
                                2,
                                1,
                                new BigDecimal("25.45"),
                                "EUR"
                        )
                ),
                //Test 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)
                new PriceTestParams(
                        LocalDateTime.of(2020, 6, 14, 21, 0),
                        new Price(
                                3,
                                1,
                                LocalDateTime.of(2020, 6, 14, 15, 0),
                                LocalDateTime.of(2020, 6, 14, 18, 30),
                                35455,
                                2,
                                1,
                                new BigDecimal("25.45"),
                                "EUR"
                        )
                ),
                //Test 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
                new PriceTestParams(
                        LocalDateTime.of(2020, 6, 15, 10, 0),
                        new Price(
                                1,
                                1,
                                LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                                35455,
                                1,
                                0,
                                new BigDecimal("35.50"),
                                "EUR"
                        )
                ),
                //Test 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)
                new PriceTestParams(
                        LocalDateTime.of(2020, 6, 16, 21, 0),
                        new Price(
                                1,
                                1,
                                LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                                1,
                                35455,
                                0,
                                new BigDecimal("35.50"),
                                "EUR"
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("priceProvider")
    void givenVariousParameters_whenFindingPriceDetails_thenTestShouldPass(PriceTestParams params) {
        Mockito.when(priceRepository.findPriceDetails(params.dateTime, 1, 35455)).
                thenReturn(Optional.ofNullable(params.expectedPrice));

        Price priceDetails = priceDetailsFinder.run(params.dateTime, 1, 35455);

        assertAll("priceDetails",
                () -> assertNotNull(priceDetails),
                () -> assertEquals(params.expectedPrice.getPrice(), priceDetails.getPrice())
        );
    }

    private static class PriceTestParams {
        LocalDateTime dateTime;
        Price expectedPrice;

        PriceTestParams(LocalDateTime dateTime, Price expectedPrice) {
            this.dateTime = dateTime;
            this.expectedPrice = expectedPrice;
        }
    }
}
