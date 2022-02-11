package com.nicolas.stocksapi.data.datasources.postgre;

import com.nicolas.stocksapi.data.datasources.IStocksDatasource;
import com.nicolas.stocksapi.data.models.StockModel;
import com.nicolas.stocksapi.domain.entities.BidAskEntity;
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

	@Override
	public Either<Exception, StockEntity> updateBidAsk(BidAskEntity bidAsk) {
		var type = bidAsk.type == 0 ? "ask" : "bid";
		String sqlString = String.format("""
		update %1$s 
		set %2$s_min = 
			case 
				when %3$s < %2$s_min then %3$s
				when %2$s_min is null then %3$s
				else %2$s_min 
			end, 
			%2$s_max = 
			case 
				when %3$s > %2$s_max then %3$s
				when %2$s_max is null then %3$s
				else %2$s_max 
			end 
		where id = %4$s 
		returning *""", 
		tableName, type, bidAsk.value.toString(), bidAsk.id_stock.toString());

		return super.execute(sqlString).map((list) -> {
			return StockModel.fromMap(list.get(0));
		});
	}
}
