package com.nicolas.stocksapi.domain.usecases;

import java.util.List;

import com.nicolas.stocksapi.core.IUsecase;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;

import io.vavr.control.Either;

public class GetStockHistoryUsecase implements IUsecase<StockEntity, List<StockEntity>>{
    private IStocksRepository repository;

    public GetStockHistoryUsecase(IStocksRepository stocksRepository) {
        repository = stocksRepository;
    }

    @Override
    public Either<Exception, List<StockEntity>> call(StockEntity stock) {
        return repository.getStockHistory(stock);
    }
    
}
