package com.nicolas.stocksapi.data.datasources.postgre;

import com.nicolas.stocksapi.data.datasources.IStocksDatasource;
import com.nicolas.stocksapi.data.models.StockModel;

import io.vavr.collection.List;
import io.vavr.control.Either;

public class PostgreStocksDataSource extends PostgreDatasource implements IStocksDatasource {

	public PostgreStocksDataSource() {
		super.databaseUrl = "localhost:5432";
		super.databaseName = "stocks_db";
		super.username = "postgres";
		super.password = "postgres";
		super.tableName = "stocks";
	}

	@Override
    public Either<Exception, List<StockModel>> getStocksList() {
        var sqlString = String.format("SELECT * FROM %S", tableName);

		return super.executeQuery(sqlString).map((list) -> {
			return list.map((e) -> StockModel.fromMap(e));
		});
    }

	@Override
	public Either<Exception, StockModel> getStock(StockModel stock) {
		var sqlString = String.format("SELECT * FROM %S WHERE id = %s", tableName, stock.id);

		return super.executeQuery(sqlString).map((list) -> {
			if (list.length() == 0) return null;
			return StockModel.fromMap(list.get(0));
		});
	}
}
