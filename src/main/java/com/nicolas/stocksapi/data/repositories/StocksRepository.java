package com.nicolas.stocksapi.data.repositories;

import java.util.List;

import com.nicolas.stocksapi.core.BidAskHelper;
import com.nicolas.stocksapi.data.datasources.IStocksDatasource;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;

import io.vavr.control.Either;

public class StocksRepository implements IStocksRepository {
	private IStocksDatasource datasource;

	public StocksRepository(IStocksDatasource stocksDatasource) {
		datasource = stocksDatasource;
	} 

    @Override
    public Either<Exception, List<StockEntity>> getStocksList() {
		return datasource.getStocksList().map(io.vavr.collection.List::asJava);
    }

	@Override
	public Either<Exception, StockEntity> getStock(StockEntity stock) {
		return datasource.getStock(stock);
	}

	@Override
	public Either<Exception, List<StockEntity>> getRandomStocks(int qty) {
		return datasource.getRandomStocks(qty).map(io.vavr.collection.List::asJava);
	}
	
	@Override
	public Either<Exception, StockEntity> checkNewBidAsk(BidAskHelper bidAsk) {
		return datasource.checkNewBidAsk(bidAsk);
	}

	@Override
	public Either<Exception, StockEntity> updateStockValues(StockEntity stock) {
		return datasource.updateStockValues(stock);
	}

	@Override
	public Either<Exception, List<StockEntity>> getStockHistory(StockEntity stock) {
		return datasource.getStockHistory(stock).map(io.vavr.collection.List::asJava);
	}
    
}
