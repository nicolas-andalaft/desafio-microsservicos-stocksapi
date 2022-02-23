package com.nicolas.stocksapi.domain.usecases;

import java.math.BigDecimal;
import java.util.Random;

import com.nicolas.stocksapi.core.BidAskHelper;
import com.nicolas.stocksapi.core.IUsecase;
import com.nicolas.stocksapi.core.NoParams;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;

import io.vavr.control.Either;

public class GenerateRandomStockHistoryUsecase implements IUsecase<NoParams, NoParams>{
    private IStocksRepository repository;

    public GenerateRandomStockHistoryUsecase(IStocksRepository stocksRepository) {
        repository = stocksRepository;
    }

    @Override
    public Either<Exception, NoParams> call(NoParams params) {
        var stocks = repository.getStocksList();
        if (stocks.isLeft())
            return Either.left(stocks.getLeft());
        
        var stocksList = stocks.get();

        var bidAsk = new BidAskHelper();
        var rand = new Random();

        System.out.println("STARTING");
        
        for(int i = 0; i < stocksList.size(); i++) {
            bidAsk.id_stock = stocksList.get(i).id;
            
            bidAsk.type = 0;
            for(int j = 0; j < 5; j++) {
                bidAsk.value = BigDecimal.valueOf(rand.nextDouble(50));
                repository.checkNewBidAsk(bidAsk);
            }
            
            bidAsk.type = 1;
            for(int j = 0; j < 5; j++) {
                bidAsk.value = BigDecimal.valueOf(rand.nextDouble(50));
                repository.checkNewBidAsk(bidAsk);
            }
        }

        System.out.println("FINISH");

        return Either.right(params);
    }
}
