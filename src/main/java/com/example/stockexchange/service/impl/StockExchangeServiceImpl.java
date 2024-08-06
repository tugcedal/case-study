package com.example.stockexchange.service.impl;

import com.example.stockexchange.domain.bo.StockBo;
import com.example.stockexchange.domain.bo.StockExchangeBo;
import com.example.stockexchange.helper.StockExchangeValidator;
import com.example.stockexchange.service.StockExchangeService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class StockExchangeServiceImpl implements StockExchangeService {
    private final StockExchangePersistenceServiceImpl stockExchangePersistenceService;
    private final StockPersistenceServiceImpl stockPersistenceService;
    private final StockExchangeValidator stockExchangeValidator;

    private static final Logger LOGGER = LogManager.getLogger(StockExchangeServiceImpl.class);

    private final Predicate<StockExchangeBo> hasMoreThanFiveStocks = se -> se.getStocks().size() >= 5;

    public StockExchangeBo getStockExchange(String name) {
        LOGGER.info("Stock exchange service get stock exchange");
        return stockExchangePersistenceService.retrieveStockExchange(name);
    }

    @Transactional
    public StockExchangeBo createStockExchange(StockExchangeBo stockExchange) {
        LOGGER.info("Stock exchange service create stock exchange");
        stockExchangeValidator.validateCreate(stockExchange);
        return stockExchangePersistenceService.saveOrUpdateStockExchange(stockExchange);
    }

    @Transactional
    public StockExchangeBo addStockToExchange(String stockExchangeName, String stockName) {
        LOGGER.info("Stock exchange service add stock to stock exchange");

        StockExchangeBo stockExchange = stockExchangePersistenceService.retrieveStockExchange(stockExchangeName);

        StockBo stockBo = stockPersistenceService.retrieveStock(stockName);

        stockBo.getStockExchanges().add(stockExchange);
        stockExchange.getStocks().add(stockBo);

        stockExchange.setLiveInMarket(hasMoreThanFiveStocks.test(stockExchange));

        stockPersistenceService.saveOrUpdateStock(stockBo);
        return stockExchangePersistenceService.saveOrUpdateStockExchange(stockExchange);
    }

    @Transactional
    public void removeStockFromExchanges(List<String> stockExchangeNameList, String stockName) {
        LOGGER.info("Stock exchange service remove stock from stock exchanges");

        if (!stockExchangeNameList.isEmpty()) {
            List<StockExchangeBo> stockExchanges = stockExchangePersistenceService.retrieveStockExchanges(stockExchangeNameList);

            StockBo stock = stockPersistenceService.retrieveStock(stockName);

            stockExchanges.forEach(stockExchange -> {
                        stockExchange.getStocks().remove(stock);
                        stockExchange.setLiveInMarket(hasMoreThanFiveStocks.test(stockExchange));
                    }
            );
            stockExchangePersistenceService.saveOrUpdateStockExchanges(stockExchanges);
        }
    }
}