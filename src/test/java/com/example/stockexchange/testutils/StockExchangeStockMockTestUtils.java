package com.example.stockexchange.testutils;

import com.example.stockexchange.domain.bo.StockBo;
import com.example.stockexchange.domain.bo.StockExchangeBo;
import com.example.stockexchange.domain.entity.StockEntity;
import com.example.stockexchange.domain.entity.StockExchangeEntity;
import com.example.stockexchange.repository.StockExchangeRepository;
import com.example.stockexchange.repository.StockRepository;
import com.example.stockexchange.service.impl.StockExchangePersistenceServiceImpl;
import com.example.stockexchange.service.impl.StockPersistenceServiceImpl;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class StockExchangeStockMockTestUtils {

    @Mock
    public static StockExchangePersistenceServiceImpl stockExchangePersistenceService;
    @Mock
    public static StockRepository stockRepository;
    @Mock
    public static StockExchangeRepository stockExchangeRepository;
    @Mock
    public static StockPersistenceServiceImpl stockPersistenceService;

    protected void onRetrieveStockCalledReturn(String stockName, StockBo stockBo) {
        when(stockPersistenceService.retrieveStock(stockName)).thenReturn(stockBo);

    }

    protected void onGetStockCalledReturn(String stockName, StockBo stockBo) {
        when(stockPersistenceService.getStock(stockName)).thenReturn(Optional.ofNullable(stockBo));

    }

    protected static void doNothingWhenDeleteStockByNameCalled() {
        doNothing().when(stockRepository).deleteByName(any());
    }


    protected static void onStockSaveCalled() {
        when(stockRepository.save(any())).thenReturn(new StockEntity());
    }

    protected static void onStockFindByNameCalled() {
        when(stockRepository.findByName(any())).thenReturn(Optional.of(new StockEntity()));
    }

    protected void onStockSaveOrUpdateCalledReturn(StockBo stockBo) {
        when(stockPersistenceService.saveOrUpdateStock(stockBo)).thenReturn(stockBo);
    }

    protected void onGetStockExchangeCalledReturn(String stockName, StockExchangeBo stockExchangeBo) {
        when(stockExchangePersistenceService.getStockExchange(stockName)).thenReturn(Optional.ofNullable(stockExchangeBo));

    }


    protected void onRetrieveStockExchangeCalledReturn(String stockName, StockExchangeBo stockExchangeBo) {
        when(stockExchangePersistenceService.retrieveStockExchange(stockName)).thenReturn(stockExchangeBo);

    }

    protected void onRetrieveStockExchangesCalledReturn(List<String> names, List<StockExchangeBo> stockExchangeBo) {
        when(stockExchangePersistenceService.retrieveStockExchanges(names)).thenReturn(stockExchangeBo);

    }

    protected void onFindByNameCalledReturn(String name, Optional<StockExchangeEntity> stockExchangeEntities) {
        doReturn(stockExchangeEntities).when(stockExchangeRepository).findByName(name);
    }

    protected void onFindByNameInCalledReturn(List<String> names, Optional<List<StockExchangeEntity>> stockExchangeEntities) {
        doReturn(stockExchangeEntities).when(stockExchangeRepository).findByNameIn(names);
    }

    protected static void onStockExchangeSaveOrUpdateCalledReturn(StockExchangeBo stockExchangeBo) {

        doReturn(stockExchangeBo).when(stockExchangePersistenceService).saveOrUpdateStockExchange((StockExchangeBo) any());
    }
}
