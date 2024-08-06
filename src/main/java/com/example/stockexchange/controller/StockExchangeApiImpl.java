package com.example.stockexchange.controller;

import com.company.project.api.StockExchangeApi;
import com.company.project.model.StockExchange;
import com.company.project.model.StockExchangeREQ;
import com.example.stockexchange.domain.bo.StockExchangeBo;
import com.example.stockexchange.domain.mapper.StockExchangeMapper;
import com.example.stockexchange.service.impl.StockExchangeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StockExchangeApiImpl extends BaseController implements StockExchangeApi {
    private final StockExchangeServiceImpl stockExchangeServiceImpl;
    private final StockExchangeMapper stockExchangeMapper;

    private static final Logger LOGGER = LogManager.getLogger(StockExchangeApiImpl.class);

    @Override
    public ResponseEntity<StockExchange> deleteStockFromStockExchange(String name, String stockName) {
        LOGGER.info("Remove stock request received with stock exchange name: '{}' stock name: '{}'", name, stockName);

        stockExchangeServiceImpl.removeStockFromExchanges(List.of(name), stockName);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<StockExchange> getStockExchangeByName(String name) {
        LOGGER.info("Retrieve request received with stock exchange name: '{}'", name);

        StockExchange stockExchange = stockExchangeMapper.convert(stockExchangeServiceImpl.getStockExchange(name));
        return ResponseEntity.ok(stockExchange);
    }

    @Override
    public ResponseEntity<StockExchange> postStockExchange(StockExchangeREQ body) {
        LOGGER.info("Create request received with stock exchange name: '{}'", body.getData().getName());

        StockExchangeBo createdStockExchange = stockExchangeServiceImpl.createStockExchange(stockExchangeMapper.convert(body.getData()));
        return ResponseEntity.ok(stockExchangeMapper.convert(createdStockExchange));
    }

    @Override
    public ResponseEntity<StockExchange> updateStockExchangeWithStock(String name, String stockName, StockExchangeREQ body) {
        LOGGER.info("Update stock request received with stock exchange name: '{}' stock name: '{}'", name, stockName);

        StockExchangeBo createdStockExchange = stockExchangeServiceImpl.addStockToExchange(name, stockName);
        return ResponseEntity.ok(stockExchangeMapper.convert(createdStockExchange));
    }


}
