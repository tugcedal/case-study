package com.example.stockexchange.service.impl;

import com.example.stockexchange.domain.bo.StockExchangeBo;
import com.example.stockexchange.domain.entity.StockExchangeEntity;
import com.example.stockexchange.domain.exception.ResourceNotFoundException;
import com.example.stockexchange.domain.mapper.StockExchangeEntityMapper;
import com.example.stockexchange.repository.StockExchangeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static com.example.stockexchange.testutils.StockExchangeTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StockExchangePersistenceServiceImplTest {

    @Mock
    public static StockExchangeRepository stockExchangeRepository;
    @Mock
    private StockExchangeEntityMapper stockExchangeEntityMapper;
    @Spy
    @InjectMocks
    private StockExchangePersistenceServiceImpl stockExchangePersistenceService;


    @Test
    public void should_assert_order_when_save_or_update_exchange_called() {
        StockExchangeBo stockExchangeBo = buildStockExchangeBo();
        StockExchangeEntity stockExchangeEntity = new StockExchangeEntity();
        onStockExchangeSaveCalled(stockExchangeEntity);
        onEntityMapperCalled(stockExchangeBo, stockExchangeEntity);

        stockExchangePersistenceService.saveOrUpdateStockExchange(stockExchangeBo);

        InOrder inOrder = Mockito.inOrder(stockExchangeRepository, stockExchangeEntityMapper);
        inOrder.verify(stockExchangeRepository).save(any());
        inOrder.verify(stockExchangeEntityMapper).convert((StockExchangeEntity) any());
        inOrder.verifyNoMoreInteractions();
    }


    @Test
    public void should_assert_same_size_of_stock_exchange_when_save_or_update_called() {
        List<StockExchangeBo> stockExchangeBos = List.of(buildStockExchangeBoWithStockNames("s1"), buildStockExchangeBoWithStockNames("s2"));
        StockExchangeEntity stockExchangeEntity = buildStockExchangeEntity();
        onEntityMapperCalled(stockExchangeBos.get(0), stockExchangeEntity);
        ArgumentCaptor<List<StockExchangeEntity>> argument = ArgumentCaptor.forClass(List.class);

        stockExchangePersistenceService.saveOrUpdateStockExchanges(stockExchangeBos);
        verify(stockExchangeRepository).saveAll(argument.capture());
        int actualSize = argument.getValue().size();

        int expectedSize = stockExchangeBos.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void should_throw_exception_when_non_existing_stock_retrieved() {
        onEntityMapperCalled(buildStockExchangeBo(), buildStockExchangeEntity());

        try {
            stockExchangePersistenceService.retrieveStockExchange("notExists");
        } catch (ResourceNotFoundException e) {
            String actualMessage = e.getMessage();

            String expectedMessage = "Stock not found, stock name: notExists";
            Assert.assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    public void should_assert_to_given_when_stock_exchanges_retreived() {
        Optional<List<StockExchangeEntity>> stockExchangeEntity = Optional.of(List.of(buildStockExchangeEntity(), buildStockExchangeEntity()));
        onFindByNameInCalledReturn(List.of("se1", "se2"), stockExchangeEntity);
        onEntityMapperCalled(buildStockExchangeBo(), buildStockExchangeEntity());

        List<StockExchangeBo> actualStockExchangeBos = stockExchangePersistenceService.retrieveStockExchanges(List.of("se1", "se2"));

        List<StockExchangeBo> expectedStockExchangeBos = List.of(buildStockExchangeBo(), buildStockExchangeBo());
        assertEquals(expectedStockExchangeBos, actualStockExchangeBos);
    }

    private static void onStockExchangeSaveCalled(StockExchangeEntity stockExchangeEntity) {
        doReturn(stockExchangeEntity).when(stockExchangeRepository).save((any()));
    }


    private void onFindByNameInCalledReturn(List<String> names, Optional<List<StockExchangeEntity>> stockExchangeEntities) {
        doReturn(stockExchangeEntities).when(stockExchangeRepository).findByNameIn(names);
    }

    private void onEntityMapperCalled(StockExchangeBo stockExchangeBo, StockExchangeEntity stockExchangeEntity) {
        when(stockExchangeEntityMapper.convert((StockExchangeBo) any())).thenReturn(stockExchangeEntity);
        when(stockExchangeEntityMapper.convert((StockExchangeEntity) any())).thenReturn(stockExchangeBo);
    }

}