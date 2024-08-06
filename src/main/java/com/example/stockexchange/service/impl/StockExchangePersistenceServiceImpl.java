package com.example.stockexchange.service.impl;

import com.example.stockexchange.domain.bo.StockExchangeBo;
import com.example.stockexchange.domain.entity.StockExchangeEntity;
import com.example.stockexchange.domain.exception.ResourceNotFoundException;
import com.example.stockexchange.domain.mapper.StockExchangeEntityMapper;
import com.example.stockexchange.repository.StockExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.stockexchange.utils.Constants.Error.STOCK_NOT_FOUND;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class StockExchangePersistenceServiceImpl {

    private final StockExchangeRepository stockExchangeRepository;
    private final StockExchangeEntityMapper stockExchangeEntityMapper;

    private static final Logger LOGGER = LogManager.getLogger(StockExchangePersistenceServiceImpl.class);


    public StockExchangeBo saveOrUpdateStockExchange(StockExchangeBo stockExchangeBo) {
        LOGGER.info("Stock exchange Persistence Service save stock exchange");

        StockExchangeEntity stockExchangeEntity = stockExchangeRepository
                .save(stockExchangeEntityMapper.convert(stockExchangeBo));

        return stockExchangeEntityMapper.convert(stockExchangeEntity);
    }

    public void saveOrUpdateStockExchanges(List<StockExchangeBo> stockExchangeEntity) {
        LOGGER.info("Stock exchange Persistence Service save stock exchanges");

        List<StockExchangeEntity> stockExchangeEntities = stockExchangeEntity.stream().map(stockExchangeEntityMapper::convert).collect(toList());
        stockExchangeRepository.saveAll(stockExchangeEntities);
    }

    public Optional<StockExchangeBo> getStockExchange(String name) {
        LOGGER.info("Stock exchange Persistence Service get stock exchange");

        Optional<StockExchangeEntity> stockExchange = stockExchangeRepository.findByName(name);


        return stockExchange.map(stockExchangeEntityMapper::convert);
    }

    public StockExchangeBo retrieveStockExchange(String name) {
        LOGGER.info("Stock exchange Persistence Service retrieve stock exchange");

        StockExchangeEntity stockExchange = stockExchangeRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(format(STOCK_NOT_FOUND, name)));

        return stockExchangeEntityMapper.convert(stockExchange);
    }

    public List<StockExchangeBo> retrieveStockExchanges(List<String> name) {
        LOGGER.info("Stock exchange Persistence Service retireve stock exchanges");

        List<StockExchangeEntity> stockExchangeEntities = stockExchangeRepository.findByNameIn(name).orElseThrow(() -> new ResourceNotFoundException(format(STOCK_NOT_FOUND, name)));
        return stockExchangeEntities.stream().map(stockExchangeEntityMapper::convert).collect(toList());

    }
}
