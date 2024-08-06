package com.example.stockexchange.domain.mapper;

import com.company.project.model.StockExchange;
import com.example.stockexchange.domain.bo.StockExchangeBo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class StockExchangeMapper {

    @Mapping(target = "id", ignore = true)
    public abstract StockExchangeBo convert(StockExchange stockExchange);

    public abstract StockExchange convert(StockExchangeBo stockExchangeDTO);
}
