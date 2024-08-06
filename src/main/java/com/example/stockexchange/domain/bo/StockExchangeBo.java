package com.example.stockexchange.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "stocks")
public class StockExchangeBo extends BusinessObject {

    private String name;
    private String description;
    private boolean liveInMarket;
    @ToString.Exclude
    private Set<StockBo> stocks = new HashSet<>();
}
