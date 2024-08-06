package com.example.stockexchange.controller;

import com.company.project.model.Stock;
import com.company.project.model.StockREQ;
import com.example.stockexchange.domain.bo.StockBo;
import com.example.stockexchange.domain.exception.ResourceAlreadyExists;
import com.example.stockexchange.domain.exception.ResourceNotFoundException;
import com.example.stockexchange.domain.mapper.StockMapper;
import com.example.stockexchange.service.impl.StockServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StockApiImplTest {

    @Mock
    private StockServiceImpl stockServiceImpl;
    @Mock
    private StockMapper stockMapper;
    @InjectMocks
    private StockApiImpl stockApi;


    @Test
    public void should_return_ok_when_create_called_without_duplicate_stock() {
        onCreateStockServiceCalled();
        onStockMapperConvertCalled();

        ResponseEntity<Stock> actualRes = stockApi.postStock(buildStockReq());

        assertEquals(HttpStatus.OK, actualRes.getStatusCode());
    }

    @Test
    public void should_throw_exception_create_called_when_duplicate_stock() {
        onCreateStockServiceCalledThrow();

        try {
            stockApi.postStock(buildStockReq());
        } catch (ResourceAlreadyExists e) {
            String actualMessage = e.getMessage();

            String expectedMessage = "Resource Already exists";
            Assert.assertEquals(expectedMessage, actualMessage);
        }
    }


    @Test
    public void should_update_stock_when_existing_stock_received() {
        onUpdateStockServiceCalled();
        onStockMapperConvertCalled();

        ResponseEntity<Stock> actualRes = stockApi.updateStock("stockName", buildStockReq());

        assertEquals(HttpStatus.OK, actualRes.getStatusCode());
    }

    @Test
    public void should_delete_stock_when_existing_stock_recieved() {
        onDeleteStockServiceCalled();
        onStockMapperConvertCalled();

        ResponseEntity<Void> actualRes = stockApi.deleteStock("stockName");

        assertEquals(HttpStatus.OK, actualRes.getStatusCode());
    }

    @Test
    public void should_trow_exception_when_non_exsiting_stock_delete_received() {
        onDeleteStockServiceCalledThrow();

        try {
            stockApi.deleteStock("stockName");
        } catch (ResourceNotFoundException e) {
            String actualMessage = e.getMessage();

            String expectedMessage = "Resource does not exists";
            Assert.assertEquals(expectedMessage, actualMessage);
        }
    }

    private StockREQ buildStockReq() {
        StockREQ stockREQ = new StockREQ();
        Stock stock = new Stock();
        stock.setCurrentPrice(10.0);
        stockREQ.setData(stock);
        return stockREQ;
    }

    private void onStockMapperConvertCalled() {
        when(stockMapper.convert((StockBo) any())).thenReturn(new Stock());
        when(stockMapper.convert((Stock) any())).thenReturn(new StockBo());
    }

    private void onUpdateStockServiceCalled() {
        when(stockServiceImpl.updateStockPrice(any(), any())).thenReturn(new StockBo());
    }

    private void onDeleteStockServiceCalled() {
        doNothing().when(stockServiceImpl).deleteStock(any());
    }

    private void onDeleteStockServiceCalledThrow() {
        doThrow(new ResourceNotFoundException("Resource does not exists")).when(stockServiceImpl).deleteStock(any());
    }

    private void onCreateStockServiceCalled() {
        when(stockServiceImpl.createStock(any())).thenReturn(new StockBo());
    }

    private void onCreateStockServiceCalledThrow() {
        when(stockServiceImpl.createStock(any())).thenThrow(new ResourceAlreadyExists("Resource Already exists"));
    }
}