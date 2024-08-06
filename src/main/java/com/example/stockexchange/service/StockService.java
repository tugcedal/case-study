package com.example.stockexchange.service;

import com.example.stockexchange.domain.bo.StockBo;

public interface StockService {

    StockBo createStock(StockBo stockBo);

    StockBo updateStockPrice(String stockName, Double newPrice);

    void deleteStock(String stockName);
}
