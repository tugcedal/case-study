package com.example.stockexchange.domain.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "stockExchanges")
public class StockBo extends BusinessObject {

    private String name;
    private String description;
    private Double currentPrice;
    private LocalDateTime lastUpdate;
    @JsonIgnore
    @ToString.Exclude
    private Set<StockExchangeBo> stockExchanges = new HashSet<>();
}
