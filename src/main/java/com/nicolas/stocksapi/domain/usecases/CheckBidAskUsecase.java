package com.nicolas.stocksapi.domain.usecases;

import com.nicolas.stocksapi.core.BidAskHelper;
import com.nicolas.stocksapi.core.IUsecase;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;

import io.vavr.control.Either;

public class CheckBidAskUsecase implements IUsecase<BidAskHelper, StockEntity>{
    private IStocksRepository repository;

    public CheckBidAskUsecase(IStocksRepository stocksRepository) {
        repository = stocksRepository;
    }

    @Override
    public Either<Exception, StockEntity> call(BidAskHelper bidAsk) {
        return repository.checkNewBidAsk(bidAsk);
    }
}
