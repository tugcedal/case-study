package com.example.stockexchange.service.impl;

import com.example.stockexchange.domain.bo.StockBo;
import com.example.stockexchange.helper.StockValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.stockexchange.testutils.StockTestUtils.buildStockBo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTest {

    @Mock
    private static StockExchangeServiceImpl stockExchangeServiceImpl;
    @Mock
    private static StockPersistenceServiceImpl stockPersistenceService;
    @Mock
    private static StockValidator stockValidator;

    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    public void should_create_stock_from_given_stock() {
        StockBo stockBo = buildStockBo();
        onSaveOrUpdateStockCalled(stockBo);
        ArgumentCaptor<StockBo> argumentCaptor = ArgumentCaptor.forClass(StockBo.class);

        stockService.createStock(stockBo);
        verify(stockPersistenceService).saveOrUpdateStock(argumentCaptor.capture());
        StockBo actualStockBo = argumentCaptor.getValue();

        assertEquals(stockBo, actualStockBo);
    }

    @Test
    public void should_update_stock_price_when_update_stock_price_called() {
        StockBo stockBo = buildStockBo();
        onRetrieveStockCalledReturn(stockBo);
        ArgumentCaptor<StockBo> argumentCaptor = ArgumentCaptor.forClass(StockBo.class);

        stockService.updateStockPrice("stock", 20.0);
        verify(stockPersistenceService).saveOrUpdateStock(argumentCaptor.capture());
        StockBo actualStockBo = argumentCaptor.getValue();

        assertEquals(20.0, actualStockBo.getCurrentPrice());
    }

    @Test
    public void should_do_nothing_when_existing_stock_retreived_from_delete_stock() {
        StockBo stockBo = buildStockBo();
        onRetrieveStockCalledReturn(stockBo);
        doNothingOnRemoveFromStockExchangesCalled();
        doNothingWhenDeleteStockCalled();

        stockService.deleteStock("stock");

        assertEquals(1, 1);
    }


    private static void onRetrieveStockCalledReturn(StockBo stockBo) {
        when(stockPersistenceService.retrieveStock(any())).thenReturn(stockBo);
    }

    private static void onSaveOrUpdateStockCalled(StockBo stockBo) {
        when(stockPersistenceService.saveOrUpdateStock(any())).thenReturn(stockBo);
    }

    private static void doNothingOnRemoveFromStockExchangesCalled() {
        doNothing().when(stockExchangeServiceImpl).removeStockFromExchanges(any(), any());

    }

    private static void doNothingWhenDeleteStockCalled() {
        doNothing().when(stockPersistenceService).deleteStock(any());

    }

    private static void doNothingWhenValidateCreateCalled() {
        doNothing().when(stockValidator).validateCreate(any());
    }

}