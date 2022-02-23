package com.nicolas.stocksapi.domain.usecases;

import java.util.List;

import com.nicolas.stocksapi.core.IUsecase;
import com.nicolas.stocksapi.core.NoParams;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;

import io.vavr.control.Either;

public class GetStocksListUsecase implements IUsecase<NoParams, List<StockEntity>>  {
    private final IStocksRepository stocksRepository;

    public GetStocksListUsecase(IStocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    @Override
    public Either<Exception, List<StockEntity>> call(NoParams params) {
        return stocksRepository.getStocksList();
    }
}
