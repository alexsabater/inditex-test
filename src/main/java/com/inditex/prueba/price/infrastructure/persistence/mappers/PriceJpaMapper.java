package com.inditex.prueba.price.infrastructure.persistence.mappers;

import com.inditex.prueba.price.domain.models.Price;
import com.inditex.prueba.price.infrastructure.persistence.entities.PriceJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceJpaMapper {
    PriceJpaMapper INSTANCE = Mappers.getMapper(PriceJpaMapper.class);

    PriceJpaEntity domainToJpa(Price dispenser);

    Price jpaToDomain(PriceJpaEntity dispenserJpaEntity);
}
