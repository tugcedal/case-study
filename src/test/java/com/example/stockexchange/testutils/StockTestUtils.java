package com.example.stockexchange.testutils;

import com.example.stockexchange.domain.bo.StockBo;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class StockTestUtils {

    private static final String STOCK_NAME = "stockName";
    private static final String STOCK_DESCRIPTION = "stockDescription";
    private static final Double DEFAULT_PRICE = 10.0;


    public static Set<StockBo> buildStockBo(String... names) {
        Set<StockBo> stockBos = new HashSet<>();

        for (String name : names) {
            stockBos.add(buildStockBoWithName(name));
        }

        return stockBos;
    }

    public static StockBo buildStockBo(String name, Double newPrice) {
        StockBo stockBo = new StockBo();
        stockBo.setName(name);
        stockBo.setDescription("stockDescription");
        stockBo.setCurrentPrice(newPrice);
        stockBo.setLastUpdate(LocalDateTime.now());

        return stockBo;
    }

    public static StockBo buildStockBoWithName(String name) {
        StockBo stockBo = buildStockBo(name, DEFAULT_PRICE);
        stockBo.setDescription(STOCK_DESCRIPTION);
        stockBo.setCurrentPrice(DEFAULT_PRICE);
        stockBo.setLastUpdate(LocalDateTime.now());

        return stockBo;
    }

    public static StockBo buildStockBo() {
        StockBo stockBo = buildStockBo(STOCK_NAME, DEFAULT_PRICE);
        stockBo.setDescription(STOCK_DESCRIPTION);
        stockBo.setLastUpdate(LocalDateTime.now());

        return stockBo;
    }
}
