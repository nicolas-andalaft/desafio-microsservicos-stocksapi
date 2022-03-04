package com.nicolas.stocksapi.domain.repositories;

import java.util.List;

import com.nicolas.stocksapi.core.BidAskHelper;
import com.nicolas.stocksapi.domain.entities.StockEntity;

import io.vavr.control.Either;


public interface IStocksRepository {
    public Either<Exception, List<StockEntity>> getStocksList();
    public Either<Exception, StockEntity> getStock(StockEntity stock);
    public Either<Exception, List<StockEntity>> getRandomStocks(int qty);
    public Either<Exception, StockEntity> checkNewBidAsk(BidAskHelper bidAsk);
    public Either<Exception, StockEntity> updateStockValues(StockEntity stock);
    public Either<Exception, List<StockEntity>> getStockHistory(StockEntity stock);
}
