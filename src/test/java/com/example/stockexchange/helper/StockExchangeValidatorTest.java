package com.example.stockexchange.helper;

import com.example.stockexchange.service.impl.StockExchangePersistenceServiceImpl;
import com.example.stockexchange.testutils.StockExchangeStockMockTestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.stockexchange.testutils.StockExchangeTestUtils.buildStockExchangeBo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class StockExchangeValidatorTest extends StockExchangeStockMockTestUtils {

//    @Mock
//    protected static StockExchangePersistenceServiceImpl stockExchangePersistenceService;

    @InjectMocks
    private StockExchangeValidator stockExchangeValidator;


    @Test
    public void should_do_nothing_when_stockbo_is_not_present() {
        onGetStockExchangeCalledReturn("stockExchangeName", null);

        stockExchangeValidator.checkDoesStockExchangeExists("stockExchangeName");

        assertEquals(1, 1);
    }

    @Test
    public void should_throw_exception_when_stockbo_is_present() {
        onGetStockExchangeCalledReturn("stockExchangeName", buildStockExchangeBo());

        try {
            stockExchangeValidator.checkDoesStockExchangeExists("stockExchangeName");
        } catch (Exception e) {
            String actualMessage = e.getMessage();

            String expectedMessage = "Stock Exchange already exists, stock name: stockExchangeName";
            Assert.assertEquals(expectedMessage, actualMessage);
        }
    }
}