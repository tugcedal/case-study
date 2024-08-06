package com.example.stockexchange.service.impl;

import com.example.stockexchange.domain.bo.StockBo;
import com.example.stockexchange.domain.bo.StockExchangeBo;
import com.example.stockexchange.helper.StockValidator;
import com.example.stockexchange.service.StockService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockExchangeServiceImpl stockExchangeServiceImpl;
    private final StockPersistenceServiceImpl stockPersistenceService;
    private final StockValidator stockValidator;

    private static final Logger LOGGER = LogManager.getLogger(StockServiceImpl.class);


    @Transactional
    public StockBo createStock(StockBo stockBo) {
        LOGGER.info("Stock Service create stock");
        stockValidator.validateCreate(stockBo);
        return stockPersistenceService.saveOrUpdateStock(stockBo);
    }

    @Transactional
    public StockBo updateStockPrice(String stockName, Double newPrice) {
        LOGGER.info("Stock Service update stock price");
        StockBo existingStockBo = stockPersistenceService.retrieveStock(stockName);
        existingStockBo.setCurrentPrice(newPrice);
        existingStockBo.setLastUpdate(LocalDateTime.now());

        return stockPersistenceService.saveOrUpdateStock(existingStockBo);
    }

    @Transactional
    public void deleteStock(String stockName) {
        LOGGER.info("Stock Service delete stock");
        StockBo stockBo = stockPersistenceService.retrieveStock(stockName);

        List<String> stockExchangeNameList = stockBo.getStockExchanges().stream().map(StockExchangeBo::getName).collect(Collectors.toList());

        stockExchangeServiceImpl.removeStockFromExchanges(stockExchangeNameList, stockName);
        stockPersistenceService.deleteStock(stockName);
    }
}