package com.inditex.prueba.price.infrastructure.rest.controllers;

import com.inditex.prueba.price.application.usecases.pricedetailsfinder.PriceDetailsFinderImpl;
import com.inditex.prueba.price.domain.models.Price;
import com.inditex.prueba.price.infrastructure.rest.mappers.PriceResponsesRestMapper;
import com.inditex.prueba.price.infrastructure.rest.responses.PriceDetailsGetterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Tag(name = PriceGetterController.SWAGGER_TAG, description = "Product details controller")
@Validated
public class PriceGetterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceGetterController.class);
    public static final String SWAGGER_TAG = "Get price controller";
    public static final String PRICE_GETTER = "/price";
    private static final PriceResponsesRestMapper priceResponsesRestMapper = PriceResponsesRestMapper.INSTANCE;
    private final PriceDetailsFinderImpl priceDetailsFinder;

    public PriceGetterController(PriceDetailsFinderImpl priceDetailsFinder) {
        this.priceDetailsFinder = priceDetailsFinder;
    }

    @GetMapping(PRICE_GETTER)
    @Operation(summary = "Get Price Details", description = "Provides price details for a specific product based on the date, product ID, and brand ID.")
    public ResponseEntity<PriceDetailsGetterResponse> getPriceDetails(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date,
            @RequestParam Integer productId,
            @RequestParam Integer brandId
    ) {
        LOGGER.info("Retrieving price details for date: {}, product ID: {}, brand ID: {}", date, productId, brandId);
        Price price = priceDetailsFinder.run(date, productId, brandId);
        PriceDetailsGetterResponse response = priceResponsesRestMapper.domainToPriceDetailsGetterResponse(price);
        return ResponseEntity.ok(response);
    }
}
