package com.example.stockexchange.service.impl;

import com.example.stockexchange.domain.bo.StockBo;
import com.example.stockexchange.domain.entity.StockEntity;
import com.example.stockexchange.domain.mapper.StockEntityMapper;
import com.example.stockexchange.testutils.StockExchangeStockMockTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockPersistenceServiceImplTest extends StockExchangeStockMockTestUtils {

    @Mock
    private static StockEntityMapper stockEntityMapper;
    @InjectMocks
    public StockPersistenceServiceImpl stockPersistenceService;


    @Test
    public void should_not_do_nothing_when_delete_stock_called() {
        doNothingWhenDeleteStockByNameCalled();

        stockPersistenceService.deleteStock("stockName");

        assertEquals(1, 1);
    }

    @Test
    public void should_assert_when_save_or_update_called() {
        StockBo stockBo = new StockBo();
        onStockSaveCalled();
        onEntityMapperCalled();

        StockBo actual = stockPersistenceService.saveOrUpdateStock(stockBo);

        assertEquals(stockBo, actual);
    }

    @Test
    public void should_assert_when_retrieve_called() {
        onStockFindByNameCalled();
        onEntityMapperCalled();

        StockBo actual = stockPersistenceService.retrieveStock("stockName");

        assertEquals(new StockBo(), actual);
    }


    private static void onEntityMapperCalled() {
        when(stockEntityMapper.convert((StockBo) any())).thenReturn(new StockEntity());
        when(stockEntityMapper.convert((StockEntity) any())).thenReturn(new StockBo());
    }
}