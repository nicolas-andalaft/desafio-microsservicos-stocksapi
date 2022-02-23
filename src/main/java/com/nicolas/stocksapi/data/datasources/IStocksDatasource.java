package com.nicolas.stocksapi.data.datasources;

import com.nicolas.stocksapi.domain.entities.BidAskEntity;
import com.nicolas.stocksapi.domain.entities.StockEntity;

import io.vavr.collection.List;
import io.vavr.control.Either;

public interface IStocksDatasource {
    public Either<Exception, List<StockEntity>> getStocksList(); 
    public Either<Exception, StockEntity> getStock(StockEntity stock); 
    public Either<Exception, List<StockEntity>> getRandomStocks(int qty); 
    public Either<Exception, StockEntity> updateBidAsk(BidAskEntity bidAsk);
    public Either<Exception, List<StockEntity>> getStockHistory(StockEntity stock);
}
