package com.example.stockexchange.testutils;

import com.example.stockexchange.domain.bo.StockBo;
import com.example.stockexchange.domain.bo.StockExchangeBo;
import com.example.stockexchange.domain.entity.StockExchangeEntity;

import java.util.HashSet;
import java.util.Set;

public class StockExchangeTestUtils {
    private static final String STOCK_EXCHANGE_NAME = "stockExchangeName";
    private static final String STOCK_EXCHANGE_DESCRIPTION = "stockExchangeDescription";

    public static StockExchangeBo buildStockExchangeBoWithStocks(Set<StockBo> stockBos) {
        StockExchangeBo stockExchangeBo = new StockExchangeBo();
        stockExchangeBo.setName(STOCK_EXCHANGE_NAME);
        stockExchangeBo.setDescription(STOCK_EXCHANGE_DESCRIPTION);
        stockExchangeBo.setLiveInMarket(stockBos.size() >= 5 ? true : false);
        stockExchangeBo.setStocks(stockBos);

        return stockExchangeBo;
    }

    public static StockExchangeBo buildStockExchangeBoWithStockNames(String... stockNames) {
        return buildStockExchangeBoWithStocks(StockTestUtils.buildStockBo(stockNames));
    }

    public static StockExchangeBo buildStockExchangeBo() {
        return buildStockExchangeBoWithStocks(new HashSet<>());

    }
    public static StockExchangeEntity buildStockExchangeEntity() {
        StockExchangeEntity stockExchangeEntity = new StockExchangeEntity();
        stockExchangeEntity.setId(1L);
        stockExchangeEntity.setLiveInMarket(false);
        stockExchangeEntity.setStocks(new HashSet<>());
        stockExchangeEntity.setName(STOCK_EXCHANGE_NAME);
        stockExchangeEntity.setDescription(STOCK_EXCHANGE_DESCRIPTION);

        return stockExchangeEntity;
    }
}
