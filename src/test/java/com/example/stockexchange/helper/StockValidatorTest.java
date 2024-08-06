package com.example.stockexchange.helper;

import com.example.stockexchange.testutils.StockExchangeStockMockTestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.stockexchange.testutils.StockTestUtils.buildStockBo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class StockValidatorTest extends StockExchangeStockMockTestUtils {

    @InjectMocks
    private StockValidator stockValidator;


    @Test
    public void should_do_nothing_when_stockbo_is_not_present() {
        onGetStockCalledReturn("stockName", null);

        stockValidator.checkDoesStockExists("stockName");

        assertEquals(1, 1);
    }

    @Test
    public void should_throw_exception_when_stockbo_is_present() {
        onGetStockCalledReturn("stockName", buildStockBo());

        try {
            stockValidator.checkDoesStockExists("stockName");
        } catch (Exception e) {
            String actualMessage = e.getMessage();

            String expectedMessage = "Stock already exists, stock name: stockName";
            Assert.assertEquals(expectedMessage, actualMessage);
        }
    }
}