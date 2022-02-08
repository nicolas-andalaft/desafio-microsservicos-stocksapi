package com.nicolas.stocksapi.domain.usecases;

import java.util.List;

import com.nicolas.stocksapi.core.IUsecase;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;

import io.vavr.control.Either;

public class GetRandomStocksUsecase implements IUsecase<Integer, List<StockEntity>>{
    IStocksRepository repository;

    public GetRandomStocksUsecase(IStocksRepository stocksRepository) {
        repository = stocksRepository;
    }

    @Override
    public Either<Exception, List<StockEntity>> call(Integer qty) {
        int stocksQty = qty != null ? qty : 1;

        return repository.getRandomStocks(stocksQty).map((list) -> list.asJava());
    }
}
