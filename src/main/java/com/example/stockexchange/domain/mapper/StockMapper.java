package com.example.stockexchange.domain.mapper;

import com.company.project.model.Stock;
import com.example.stockexchange.domain.bo.StockBo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class StockMapper {
    @Mapping(target = "id", ignore = true)
    public abstract StockBo convert(Stock stockApiModel);

    public abstract Stock convert(StockBo stockBo);
}
