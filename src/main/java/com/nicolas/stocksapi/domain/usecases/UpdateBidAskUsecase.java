package com.nicolas.stocksapi.domain.usecases;

import com.nicolas.stocksapi.core.IUsecase;
import com.nicolas.stocksapi.domain.entities.BidAskEntity;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;

import io.vavr.control.Either;

public class UpdateBidAskUsecase implements IUsecase<BidAskEntity, StockEntity>{
    private IStocksRepository repository;

    public UpdateBidAskUsecase(IStocksRepository stocksRepository) {
        repository = stocksRepository;
    }

    @Override
    public Either<Exception, StockEntity> call(BidAskEntity bidAsk) {
        return repository.updateBidAsk(bidAsk);
    }
}