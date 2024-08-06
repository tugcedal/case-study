package com.example.stockexchange.helper;

import com.example.stockexchange.domain.bo.StockBo;
import com.example.stockexchange.domain.exception.ResourceAlreadyExists;
import com.example.stockexchange.service.impl.StockPersistenceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StockValidator implements IValidator<StockBo> {

    private final StockPersistenceServiceImpl stockPersistenceService;

    private static final Logger LOGGER = LogManager.getLogger(StockValidator.class);

    public void validateCreate(StockBo stockBo) {
        LOGGER.info("Stock Validator validate stock");
        checkDoesStockExists(stockBo.getName());
    }

    protected void checkDoesStockExists(String stockName) {
        Optional<StockBo> stockBo = stockPersistenceService.getStock(stockName);

        stockBo.ifPresent(stock -> {
            throw new ResourceAlreadyExists("Stock already exists, stock name: " + stockName);
        });
    }
}
