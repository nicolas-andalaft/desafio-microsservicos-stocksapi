package com.nicolas.stocksapi.data.repositories;

import com.nicolas.stocksapi.data.datasources.IStocksDatasource;
import com.nicolas.stocksapi.data.models.StockModel;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;

import io.vavr.collection.List;
import io.vavr.control.Either;

public class StocksRepository implements IStocksRepository {
	private IStocksDatasource datasource;

	public StocksRepository(IStocksDatasource stocksDatasource) {
		datasource = stocksDatasource;
	} 

    @Override
    public Either<Exception, List<StockEntity>> getStocksList() {
		return datasource.getStocksList().map((list) -> {
			return list.map((e) -> (StockEntity)e);
		});
    }

	@Override
	public Either<Exception, StockEntity> getStock(StockEntity stock) {
		return datasource.getStock(new StockModel(stock)).map((e) -> (StockEntity)e);
	}

	@Override
	public Either<Exception, List<StockEntity>> getRandomStocks(int qty) {
		return datasource.getRandomStocks(qty);
	}
    
}
