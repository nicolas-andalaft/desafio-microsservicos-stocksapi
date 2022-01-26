package com.nicolas.stocksapi.data.datasources;

import com.nicolas.stocksapi.data.models.StockModel;

import io.vavr.collection.List;
import io.vavr.control.Either;

public interface IStocksDatasource {
    public Either<Exception, List<StockModel>> getStocksList(); 
    public Either<Exception, StockModel> getStock(StockModel stock); 
}
