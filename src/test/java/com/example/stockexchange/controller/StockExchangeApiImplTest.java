package com.example.stockexchange.controller;


import com.company.project.model.Stock;
import com.company.project.model.StockExchange;
import com.company.project.model.StockExchangeREQ;
import com.example.stockexchange.domain.bo.StockExchangeBo;
import com.example.stockexchange.domain.exception.ResourceAlreadyExists;
import com.example.stockexchange.domain.exception.ResourceNotFoundException;
import com.example.stockexchange.domain.mapper.StockExchangeMapper;
import com.example.stockexchange.service.impl.StockExchangeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockExchangeApiImplTest {

    @Mock
    private StockExchangeServiceImpl stockExchangeServiceImpl;
    @Mock
    private StockExchangeMapper stockExchangeMapper;
    @InjectMocks
    private StockExchangeApiImpl stockExchangeApi;


    @Test
    public void should_return_ok_when_create_called_without_duplicate_stock() {
        onCreateStockExchangeServiceCalled();
        onStockExchangeExchangeMapperConvertCalled();

        ResponseEntity<StockExchange> actualRes = stockExchangeApi.postStockExchange(buildStockExchangeReq());

        assertEquals(HttpStatus.OK, actualRes.getStatusCode());
    }

    @Test
    public void should_throw_exception_create_called_when_duplicate_stock() {
        onCreateStockExchangeServiceCalledThrow();

        try {
            stockExchangeApi.postStockExchange(buildStockExchangeReq());
        } catch (ResourceAlreadyExists e) {
            String actualMessage = e.getMessage();

            String expectedMessage = "Resource Already exists";
            Assert.assertEquals(expectedMessage, actualMessage);
        }
    }


    @Test
    public void should_update_stock_when_existing_stock_received() {
        onGetStockExchangeServiceCalled();
        onStockExchangeExchangeMapperConvertCalled();

        ResponseEntity<StockExchange> actualRes = stockExchangeApi.getStockExchangeByName("se");

        assertEquals(HttpStatus.OK, actualRes.getStatusCode());
    }

    @Test
    public void should_get_stock_when_existing_stock_received() {
        onUpdateStockExchangeServiceCalled();
        onStockExchangeExchangeMapperConvertCalled();

        ResponseEntity<StockExchange> actualRes = stockExchangeApi.updateStockExchangeWithStock("se", "s", new StockExchangeREQ());

        assertEquals(HttpStatus.OK, actualRes.getStatusCode());
    }

    @Test
    public void should_delete_stock_when_existing_stock_recieved() {
        onDeleteStockExchangeServiceCalled();
        onStockExchangeExchangeMapperConvertCalled();

        ResponseEntity<StockExchange> actualRes = stockExchangeApi.deleteStockFromStockExchange("se", "s");

        assertEquals(HttpStatus.OK, actualRes.getStatusCode());
    }

    @Test
    public void should_trow_exception_when_non_exsiting_stock_delete_received() {
        onDeleteStockExchangeServiceCalledThrow();

        try {
            stockExchangeApi.deleteStockFromStockExchange("notExists", "s");
        } catch (ResourceNotFoundException e) {
            String actualMessage = e.getMessage();

            String expectedMessage = "Resource does not exists";
            Assert.assertEquals(expectedMessage, actualMessage);
        }
    }


    private StockExchangeREQ buildStockExchangeReq() {
        Stock stock = new Stock();
        stock.setCurrentPrice(10.0);

        StockExchange stockExchange = new StockExchange();
        stockExchange.setStocks(List.of(stock));

        StockExchangeREQ stockExchangeREQ = new StockExchangeREQ();
        stockExchangeREQ.setData(stockExchange);
        return stockExchangeREQ;
    }


    private void onStockExchangeExchangeMapperConvertCalled() {
        when(stockExchangeMapper.convert((StockExchangeBo) any())).thenReturn(new StockExchange());
        when(stockExchangeMapper.convert((StockExchange) any())).thenReturn(new StockExchangeBo());
    }

    private void onGetStockExchangeServiceCalled() {
        when(stockExchangeServiceImpl.getStockExchange(any())).thenReturn(new StockExchangeBo());
    }
    private void onUpdateStockExchangeServiceCalled() {
        when(stockExchangeServiceImpl.addStockToExchange(any(), any())).thenReturn(new StockExchangeBo());
    }

    private void onDeleteStockExchangeServiceCalled() {
        doNothing().when(stockExchangeServiceImpl).removeStockFromExchanges(any(), any());
    }

    private void onDeleteStockExchangeServiceCalledThrow() {
        doThrow(new ResourceNotFoundException("Resource does not exists")).when(stockExchangeServiceImpl).removeStockFromExchanges(any(), any());
    }

    private void onCreateStockExchangeServiceCalled() {
        when(stockExchangeServiceImpl.createStockExchange(any())).thenReturn(new StockExchangeBo());
    }

    private void onCreateStockExchangeServiceCalledThrow() {
        when(stockExchangeServiceImpl.createStockExchange(any())).thenThrow(new ResourceAlreadyExists("Resource Already exists"));
    }

}