package com.nicolas.stocksapi.domain.repositories;

import com.nicolas.stocksapi.domain.entities.StockEntity;
import io.vavr.control.Either;


public abstract class StocksRepository {
    public abstract Either<Exception, StockEntity[]> getStocksList();
}
