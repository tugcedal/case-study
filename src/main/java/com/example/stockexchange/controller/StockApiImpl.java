package com.example.stockexchange.controller;

import com.company.project.api.StockApi;
import com.company.project.model.Stock;
import com.company.project.model.StockREQ;
import com.example.stockexchange.domain.bo.StockBo;
import com.example.stockexchange.domain.mapper.StockMapper;
import com.example.stockexchange.service.StockService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class StockApiImpl extends BaseController implements StockApi {
    private final StockService stockService;
    private final StockMapper stockMapper;

    private static final Logger LOGGER = LogManager.getLogger(StockApiImpl.class);

    @Override
    public ResponseEntity<Stock> postStock(StockREQ body) {
        LOGGER.info("Create request received with stock name: '{}'", body.getData().getName());
        StockBo createdStock = stockService.createStock(stockMapper.convert(body.getData()));
        return ResponseEntity.ok(stockMapper.convert(createdStock));
    }

    @Override
    public ResponseEntity<Stock> updateStock(String name, StockREQ body) {
        LOGGER.info("Update request received with stock name: '{}'", name);
        StockBo createdStock = stockMapper.convert(body.getData());
        StockBo updatedStock = stockService.updateStockPrice(name, createdStock.getCurrentPrice());
        return ResponseEntity.ok(stockMapper.convert(updatedStock));

    }

    @Override
    public ResponseEntity<Void> deleteStock(String name) {
        LOGGER.info("Delete request received with stock name: '{}'", name);
        stockService.deleteStock(name);
        return ResponseEntity.ok().build();
    }
}
