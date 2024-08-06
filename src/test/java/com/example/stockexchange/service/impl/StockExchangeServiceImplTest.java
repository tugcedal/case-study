package com.example.stockexchange.service.impl;

import com.example.stockexchange.domain.bo.StockBo;
import com.example.stockexchange.domain.bo.StockExchangeBo;
import com.example.stockexchange.helper.StockExchangeValidator;
import com.example.stockexchange.testutils.StockExchangeStockMockTestUtils;
import com.example.stockexchange.testutils.StockTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.example.stockexchange.testutils.StockExchangeTestUtils.buildStockExchangeBo;
import static com.example.stockexchange.testutils.StockExchangeTestUtils.buildStockExchangeBoWithStockNames;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class StockExchangeServiceImplTest extends StockExchangeStockMockTestUtils {

    @Mock
    private static StockExchangeValidator stockExchangeValidator;
    @Spy
    @InjectMocks
    public StockExchangeServiceImpl stockExchangeService;


    @Test
    public void should_verify_order_when_create_stock_called() {
        StockExchangeBo stockExchangeBo = buildStockExchangeBo();

        stockExchangeService.createStockExchange(stockExchangeBo);

        InOrder inOrder = Mockito.inOrder(stockExchangeValidator, stockExchangePersistenceService);
        inOrder.verify(stockExchangeValidator).validateCreate(stockExchangeBo);
        inOrder.verify(stockExchangePersistenceService).saveOrUpdateStockExchange(stockExchangeBo);
        inOrder.verifyNoMoreInteractions();
    }


    @Test
    public void should_add_stock_to_stock_exchange_when_stock_added() {
        StockExchangeBo stockExchangeBo = buildStockExchangeBo();
        StockBo stockBo = StockTestUtils.buildStockBo();
        onRetrieveStockExchangeCalledReturn("stockExchangeName", buildStockExchangeBo());
        onRetrieveStockCalledReturn("stockName", stockBo);
        onStockSaveOrUpdateCalledReturn(stockBo);
        onStockExchangeSaveOrUpdateCalledReturn(stockExchangeBo);
        ArgumentCaptor<StockExchangeBo> argument = ArgumentCaptor.forClass(StockExchangeBo.class);

        stockExchangeService.addStockToExchange("stockExchangeName", "stockName");
        verify(stockExchangePersistenceService).saveOrUpdateStockExchange(argument.capture());
        int actualStockSize = argument.getValue().getStocks().size();

        int expectedStockSize = buildStockExchangeBo().getStocks().size() + 1;
        assertEquals(expectedStockSize, actualStockSize);
    }

    @Test
    public void should_add_stock_to_stock_exchange_and_make_live_in_market_true() {
        StockExchangeBo stockExchangeBo = buildStockExchangeBoWithStockNames("s1", "s2", "s3", "s4");
        StockBo stockBo = StockTestUtils.buildStockBoWithName("s5");
        onRetrieveStockExchangeCalledReturn("stockExchangeName", stockExchangeBo);
        onRetrieveStockCalledReturn("s5", stockBo);
        onStockSaveOrUpdateCalledReturn(stockBo);
        onStockExchangeSaveOrUpdateCalledReturn(stockExchangeBo);
        ArgumentCaptor<StockExchangeBo> argument = ArgumentCaptor.forClass(StockExchangeBo.class);

        stockExchangeService.addStockToExchange("stockExchangeName", "s5");
        verify(stockExchangePersistenceService).saveOrUpdateStockExchange(argument.capture());
        boolean actualIsLiveInMarket = argument.getValue().isLiveInMarket();

        assertEquals(true, actualIsLiveInMarket);
    }


//    @Test
//    public void asdaaaaa() {
//        StockExchangeBo stockExchangeBo = buildStockExchangeBo();
//        StockBo stockBo = StockTestUtils.buildStockBo();
//        onRetrieveStockExchangeCalledReturn("stockExchangeName", buildStockExchangeBo());
//        onRetrieveStockCalledReturn("stockName", stockBo);
//        onStockExchangeSaveOrUpdateCalledReturn(stockExchangeBo);
//        ArgumentCaptor<StockExchangeBo> argument = ArgumentCaptor.forClass(StockExchangeBo.class);
//
//        stockExchangeService.removeStockFromExchange("stockExchangeName", "stockName");
//        verify(stockExchangePersistenceService).saveOrUpdateStockExchange(argument.capture());
//
//        StockExchangeBo expectedStockExchangeBo = buildStockExchangeBo();
//        assertEquals(expectedStockExchangeBo.isLiveInMarket(), argument.getValue().isLiveInMarket());
//    }


    @Test
    public void should_not_remove_stock_from_stock_exchange_when_stock_is_not_in_the_list() {
        StockExchangeBo stockExchangeBo = buildStockExchangeBoWithStockNames("se1", "se2", "se3");
        StockBo stockBo = StockTestUtils.buildStockBo();
        onRetrieveStockExchangesCalledReturn(List.of("se1", "se2", "se3"), List.of(stockExchangeBo));
        onRetrieveStockCalledReturn("stockName", stockBo);
        onStockExchangeSaveOrUpdateCalledReturn(stockExchangeBo);
        ArgumentCaptor<List<StockExchangeBo>> argument = ArgumentCaptor.forClass(List.class);

        stockExchangeService.removeStockFromExchanges(List.of("se1", "se2", "se3"), "noExistingStockName");
        verify(stockExchangePersistenceService).saveOrUpdateStockExchanges(argument.capture());

        int expectedStockExchangeBo = buildStockExchangeBoWithStockNames("se1", "se2", "se3").getStocks().size();
        assertEquals(expectedStockExchangeBo, argument.getValue().get(0).getStocks().size());
    }

    @Test
    public void should_remove_stock_given_from_stock_exchange_when_exists() {
        StockExchangeBo stockExchangeBo = buildStockExchangeBoWithStockNames("se1", "se2", "se3");
        StockBo stockBo = StockTestUtils.buildStockBoWithName("se1");
        onRetrieveStockExchangesCalledReturn(List.of("se1", "se2", "se3"), List.of(stockExchangeBo));
        onRetrieveStockCalledReturn("se1", stockBo);
        onStockExchangeSaveOrUpdateCalledReturn(stockExchangeBo);
        ArgumentCaptor<List<StockExchangeBo>> argument = ArgumentCaptor.forClass(List.class);

        stockExchangeService.removeStockFromExchanges(List.of("se1", "se2", "se3"), "se1");
        verify(stockExchangePersistenceService).saveOrUpdateStockExchanges(argument.capture());

        boolean expected = argument.getValue().get(0).getStocks().stream().anyMatch(x -> !x.getName().equals("se1"));
        assertTrue(expected);
    }

    @Test
    public void should_live_in_market_false_when_stock_removed_from_stock_exchanges_has_more_than_five_stocks() {
        StockExchangeBo stockExchangeBo = buildStockExchangeBoWithStockNames("s1", "s2", "s3", "s4", "s5");
        StockBo stockBo = StockTestUtils.buildStockBoWithName("s5");
        onRetrieveStockExchangesCalledReturn(List.of("se1"), List.of(stockExchangeBo));
        onRetrieveStockCalledReturn("s5", stockBo);
        onStockExchangeSaveOrUpdateCalledReturn(stockExchangeBo);
        ArgumentCaptor<List<StockExchangeBo>> argument = ArgumentCaptor.forClass(List.class);

        stockExchangeService.removeStockFromExchanges(List.of("se1"), "s5");
        verify(stockExchangePersistenceService).saveOrUpdateStockExchanges(argument.capture());

        boolean actualIsLiveInMarket = argument.getValue().get(0).isLiveInMarket();
        assertEquals(false, actualIsLiveInMarket);
    }
}