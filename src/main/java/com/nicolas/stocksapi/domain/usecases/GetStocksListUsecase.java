package com.nicolas.stocksapi.domain.usecases;

import java.util.List;

import javax.validation.constraints.Null;

import com.nicolas.stocksapi.core.IUsecase;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;

import io.vavr.control.Either;

public class GetStocksListUsecase implements IUsecase<Null, List<StockEntity>>  {
    private final IStocksRepository stocksRepository;

    public GetStocksListUsecase(IStocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    @Override
    public Either<Exception, List<StockEntity>> call(Null params) {
        return stocksRepository.getStocksList();
    }
}
