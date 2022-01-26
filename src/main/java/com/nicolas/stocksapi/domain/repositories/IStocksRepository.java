package com.nicolas.stocksapi.domain.repositories;

import com.nicolas.stocksapi.domain.entities.StockEntity;

import io.vavr.collection.List;
import io.vavr.control.Either;


public interface IStocksRepository {
    public Either<Exception, List<StockEntity>> getStocksList();
    public Either<Exception, StockEntity> getStock(StockEntity stock);
}
