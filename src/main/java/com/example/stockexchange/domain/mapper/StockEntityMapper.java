package com.example.stockexchange.domain.mapper;

import com.example.stockexchange.domain.bo.StockBo;
import com.example.stockexchange.domain.entity.StockEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class StockEntityMapper {

    @Named("convert")
    public StockBo convert(StockEntity stockEntity) {
        return this.convertWithContext(stockEntity, new CycleAvoidingMappingContext());
    }

    @Named("convert")
    public StockEntity convert(StockBo stockBo) {
        return this.convertWithContext(stockBo, new CycleAvoidingMappingContext());
    }

    @Named("convertWithContext")
    public abstract StockEntity convertWithContext(StockBo stockBo, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);


    @Named("convertWithContext")
    public abstract StockBo convertWithContext(StockEntity stockEntity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

}
