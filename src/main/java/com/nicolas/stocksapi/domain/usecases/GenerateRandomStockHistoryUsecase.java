package com.nicolas.stocksapi.domain.usecases;

import java.math.BigDecimal;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.constraints.Null;

import com.nicolas.stocksapi.core.BidAskHelper;
import com.nicolas.stocksapi.core.IUsecase;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;

import io.vavr.control.Either;

public class GenerateRandomStockHistoryUsecase implements IUsecase<Null, Null>{
    private IStocksRepository repository;
    
    public GenerateRandomStockHistoryUsecase(IStocksRepository stocksRepository) {
        repository = stocksRepository;

        this.random = new Random();
    }

    private Random random;

    @Override
    public Either<Exception, Null> call(Null params) {
        var stocks = repository.getStocksList();
        if (stocks.isLeft())
            return Either.left(stocks.getLeft());
        
        var stocksList = stocks.get();

        var bidAsk = new BidAskHelper();

        var logger = Logger.getLogger("Logger");
        logger.log(Level.INFO, "RANDOM STOCKS GENERATION STARTED");
        
        for(int i = 0; i < stocksList.size(); i++) {
            bidAsk.setIdStock(stocksList.get(i).getId());
            
            bidAsk.setType(0);
            for(int j = 0; j < 5; j++) {
                bidAsk.setValue(BigDecimal.valueOf(random.nextDouble(50)));
                repository.checkNewBidAsk(bidAsk);
            }
            
            bidAsk.setType(1);
            for(int j = 0; j < 5; j++) {
                bidAsk.setValue(BigDecimal.valueOf(random.nextDouble(50)));
                repository.checkNewBidAsk(bidAsk);
            }
        }

        logger.log(Level.INFO, "RANDOM STOCKS GENERATION FINISHED");

        return Either.right(params);
    }
}
