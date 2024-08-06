package com.example.stockexchange.helper;

import com.example.stockexchange.domain.bo.StockExchangeBo;
import com.example.stockexchange.domain.exception.ResourceAlreadyExists;
import com.example.stockexchange.service.impl.StockExchangePersistenceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.stockexchange.utils.Constants.Error.STOCK_ALREADY_EXISTS;
import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class StockExchangeValidator implements IValidator<StockExchangeBo> {

    private final StockExchangePersistenceServiceImpl stockExchangePersistenceService;


    public void validateCreate(StockExchangeBo stockExchangeBo) {
        checkDoesStockExchangeExists(stockExchangeBo.getName());
    }

    protected void checkDoesStockExchangeExists(String stockExchangeName) {
        Optional<StockExchangeBo> stockExchangeBo = stockExchangePersistenceService.getStockExchange(stockExchangeName);

        stockExchangeBo.ifPresent(stock -> {
            throw new ResourceAlreadyExists(format(STOCK_ALREADY_EXISTS, stockExchangeName));
        });
    }
}
