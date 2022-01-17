package com.nicolas.stocksapi.domain.usecases;

import com.nicolas.stocksapi.core.IUsecase;
import com.nicolas.stocksapi.core.NoParams;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.StocksRepository;

import io.vavr.control.Either;

public class GetStocksUsecase implements IUsecase<NoParams, StockEntity[]>  {
    private final StocksRepository stocksRepository;

    public GetStocksUsecase(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    @Override
    public Either<Exception, StockEntity[]> call(NoParams params) {
        Either<Exception, StockEntity[]> result = stocksRepository.getStocksList();
        return result;
    }
}
