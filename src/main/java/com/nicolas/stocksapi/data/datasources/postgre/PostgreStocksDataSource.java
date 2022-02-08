package com.nicolas.stocksapi.data.datasources.postgre;

import com.nicolas.stocksapi.data.datasources.IStocksDatasource;
import com.nicolas.stocksapi.data.models.StockModel;
import com.nicolas.stocksapi.domain.entities.StockEntity;

import io.vavr.collection.List;
import io.vavr.control.Either;

public class PostgreStocksDataSource extends PostgreDatasource implements IStocksDatasource {

	public PostgreStocksDataSource() {
		super("stocks");
	}

	@Override
    public Either<Exception, List<StockEntity>> getStocksList() {
        var sqlString = String.format("SELECT * FROM %S", tableName);

		return super.execute(sqlString).map((list) -> {
			return list.map((e) -> StockModel.fromMap(e));
		});
    }

	@Override
	public Either<Exception, StockEntity> getStock(StockEntity stock) {
		var sqlString = String.format("SELECT * FROM %S WHERE id = %s", tableName, stock.id);

		return super.execute(sqlString).map((list) -> {
			if (list.length() == 0) return null;
			return StockModel.fromMap(list.get(0));
		});
	}

	@Override
	public Either<Exception, List<StockEntity>> getRandomStocks(int qty) {
		var sqlString = String.format("SELECT * FROM %s ORDER BY RANDOM() LIMIT %s", tableName, qty);

		return super.execute(sqlString).map((list) -> {
			return list.map((e) -> StockModel.fromMap(e));
		});
	}
}
