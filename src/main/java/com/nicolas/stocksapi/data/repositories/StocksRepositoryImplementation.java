package com.nicolas.stocksapi.data.repositories;

import com.nicolas.stocksapi.data.datasources.StocksDataSource;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.StocksRepository;

import io.vavr.control.Either;

public class StocksRepositoryImplementation extends StocksRepository {
	private StocksDataSource stocksDataSource;

	public StocksRepositoryImplementation() {
		stocksDataSource = new StocksDataSource();
	} 

    @Override
    public Either<Exception, StockEntity[]> getStocksList() {
		Either<Exception, StockEntity[]> result = stocksDataSource.getStocksList();

		if (result.isLeft()) 
			return Either.left(result.getLeft());
		else
			return Either.right(result.get());
        
    }
    
}
