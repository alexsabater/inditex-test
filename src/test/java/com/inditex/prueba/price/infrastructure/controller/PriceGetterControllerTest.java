package com.inditex.prueba.price.infrastructure.controller;

import com.inditex.prueba.common.exceptions.InvalidParameterException;
import com.inditex.prueba.price.application.usecases.pricedetailsfinder.PriceDetailsFinderImpl;
import com.inditex.prueba.price.domain.exceptions.PriceNotFoundException;
import com.inditex.prueba.price.domain.models.Price;
import com.inditex.prueba.price.infrastructure.rest.controllers.PriceGetterController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PriceGetterController.class)
public class PriceGetterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceDetailsFinderImpl priceDetailsFinder;

    @Test
    public void whenGivenPriceIsValid_thenReturns200() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(1);
        Price priceDetails = new Price(
                1,
                1,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                35455,
                100,
                0,
                new BigDecimal("35.50"),
                "EUR"
        );

        when(priceDetailsFinder.run(any(LocalDateTime.class), any(Integer.class), any(Integer.class)))
                .thenReturn(priceDetails);

        mockMvc.perform(get(PriceGetterController.PRICE_GETTER)
                        .param("date", dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPriceNotFound_thenReturns404() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(1);

        given(priceDetailsFinder.run(any(LocalDateTime.class), any(Integer.class), any(Integer.class)))
                .willThrow(new PriceNotFoundException());

        mockMvc.perform(get(PriceGetterController.PRICE_GETTER)
                        .param("date", dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .param("productId", "100")
                        .param("brandId", "1"))
                .andExpect(status().is(PriceNotFoundException.ERROR_CODE));
    }

    @Test
    public void whenInvalidParametersGiven_thenReturns404() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(1);

        given(priceDetailsFinder.run(any(LocalDateTime.class), any(Integer.class), any(Integer.class)))
                .willThrow(new InvalidParameterException());

        mockMvc.perform(get(PriceGetterController.PRICE_GETTER)
                        .param("date", dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .param("productId", "100")
                        .param("brandId", "-1"))
                .andExpect(status().is(InvalidParameterException.ERROR_CODE));
    }
}
