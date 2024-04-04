package com.inditex.prueba.price.infrastructure.rest.mappers;

import com.inditex.prueba.price.domain.models.Price;
import com.inditex.prueba.price.infrastructure.rest.responses.PriceDetailsGetterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceResponsesRestMapper {
    PriceResponsesRestMapper INSTANCE = Mappers.getMapper(PriceResponsesRestMapper.class);
    PriceDetailsGetterResponse domainToPriceDetailsGetterResponse(Price price);
}
