package com.nicolas.stocksapi.domain.usecases;

import com.nicolas.stocksapi.core.IUsecase;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;

import io.vavr.control.Either;

public class GetStockUsecase implements IUsecase<StockEntity, StockEntity>{
    private IStocksRepository repository;

    public GetStockUsecase(IStocksRepository stocksRepository) {
        repository = stocksRepository;
    }

    @Override
    public Either<Exception, StockEntity> call(StockEntity stock) {
        return repository.getStock(stock);
    }
    
}
