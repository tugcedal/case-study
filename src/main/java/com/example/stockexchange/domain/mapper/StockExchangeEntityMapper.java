package com.example.stockexchange.domain.mapper;

import com.example.stockexchange.domain.bo.StockExchangeBo;
import com.example.stockexchange.domain.entity.StockExchangeEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class StockExchangeEntityMapper {
    @Named("convertWithContext")
    public abstract StockExchangeBo convertWithContext(StockExchangeEntity stockExchange, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Named("convert")
    public StockExchangeBo convert(StockExchangeEntity stockExchangeEntity) {
        return convertWithContext(stockExchangeEntity, new CycleAvoidingMappingContext());
    }

    @Named("convertWithContext")
    public abstract StockExchangeEntity convertWithContext(StockExchangeBo stockExchangeBo, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Named("convert")
    public StockExchangeEntity convert(StockExchangeBo stockExchangeBo) {
        return convertWithContext(stockExchangeBo, new CycleAvoidingMappingContext());

    }

}

