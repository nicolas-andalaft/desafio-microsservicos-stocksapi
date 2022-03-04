package com.nicolas.stocksapi.data.datasources.postgre;

import com.nicolas.stocksapi.core.BidAskHelper;
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
        var sqlString = String.format("SELECT * FROM %S ORDER BY id", tableName);

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

	@Override
	public Either<Exception, StockEntity> checkNewBidAsk(BidAskHelper bidAsk) {
		var type = bidAsk.type == 0 ? "ask" : "bid";
		String sqlString = String.format("""
		UPDATE %1$s 
		SET %2$s_min = 
			CASE 
				WHEN %2$s_min > %3$s THEN %3$s
				WHEN %2$s_min IS NULL THEN %3$s
				ELSE %2$s_min 
			END,
			%2$s_max = 
			CASE 
				WHEN %2$s_max < %3$s THEN %3$s
				WHEN %2$s_max IS NULL THEN %3$s
				ELSE %2$s_max 
			END
		WHERE 
			id = %4$s AND
			(%2$s_min <> %3$s OR %2$s_min IS NULL OR
			%2$s_max <> %3$s OR %2$s_max IS NULL)
		RETURNING *""", 
		tableName, type, bidAsk.value.toString(), bidAsk.id_stock.toString());

		return super.execute(sqlString).map((list) -> {
			if (list == null || list.length() == 0) return null;
			return StockModel.fromMap(list.get(0));
		});
	}

	@Override
	public Either<Exception, StockEntity> updateStockValues(StockEntity stock) {
		String sqlString = String.format("""
		UPDATE %s 
		SET bid_min = %s, bid_max = %s, ask_min = %s, ask_max = %s
		WHERE id = %s
		RETURNING *""", 
		tableName, stock.bid_min, stock.bid_max, stock.ask_min, stock.ask_max, stock.id);

		return super.execute(sqlString).map((list) -> {
			if (list == null || list.length() == 0) return null;
			return StockModel.fromMap(list.get(0));
		});
	}

	@Override
	public Either<Exception, List<StockEntity>> getStockHistory(StockEntity stock) {
		String sqlString = String.format("""
		SELECT 
		Stock.id, Stock.stock_name, Stock.stock_symbol, 
		History.ask_min, History.ask_max, History.bid_min, History.bid_max, History.created_on
		FROM %s Stock
		INNER JOIN %s_history History ON
		Stock.id = History.id_stock
		WHERE History.id_stock = %s
		ORDER BY created_on""", 
		tableName, tableName, stock.id);

		return super.execute(sqlString).map((list) -> {
			return list.map((e) -> StockModel.fromMap(e));
		});
	}
}
