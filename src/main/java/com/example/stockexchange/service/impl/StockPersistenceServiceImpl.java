package com.example.stockexchange.service.impl;

import com.example.stockexchange.domain.bo.StockBo;
import com.example.stockexchange.domain.entity.StockEntity;
import com.example.stockexchange.domain.exception.ResourceNotFoundException;
import com.example.stockexchange.domain.mapper.StockEntityMapper;
import com.example.stockexchange.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.stockexchange.utils.Constants.Error.STOCK_NOT_FOUND;
import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class StockPersistenceServiceImpl {

    private final StockRepository stockRepository;
    private final StockEntityMapper stockEntityMapper;

    private static final Logger LOGGER = LogManager.getLogger(StockPersistenceServiceImpl.class);

    public void deleteStock(String stockName) {
        LOGGER.info("Stock Persistence service delete stock");
        stockRepository.deleteByName(stockName);
    }

    public StockBo saveOrUpdateStock(StockBo stockBo) {
        LOGGER.info("Stock Persistence service save stock");
        StockEntity stockEntity = stockRepository.save(stockEntityMapper.convert(stockBo));

        return stockEntityMapper.convert(stockEntity);
    }

    public Optional<StockBo> getStock(String stockName) {
        LOGGER.info("Stock Persistence service get stock");
        Optional<StockEntity> stockEntity = stockRepository.findByName(stockName);

        return stockEntity.map(stockEntityMapper::convert);
    }


    public StockBo retrieveStock(String stockName) {
        LOGGER.info("Stock Persistence service retireve stock");
        StockEntity stockEntity = stockRepository.findByName(stockName)
                .orElseThrow(() -> new ResourceNotFoundException(format(STOCK_NOT_FOUND, stockName)));

        return stockEntityMapper.convert(stockEntity);
    }

}
