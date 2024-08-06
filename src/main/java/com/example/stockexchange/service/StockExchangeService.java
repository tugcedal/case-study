package com.example.stockexchange.service;

import com.example.stockexchange.domain.bo.StockExchangeBo;

import java.util.List;

public interface StockExchangeService {
    StockExchangeBo getStockExchange(String name);

    StockExchangeBo createStockExchange(StockExchangeBo stockExchange);

    StockExchangeBo addStockToExchange(String stockExchangeName, String stockName);

    void removeStockFromExchanges(List<String> nameList, String stockName);
}
