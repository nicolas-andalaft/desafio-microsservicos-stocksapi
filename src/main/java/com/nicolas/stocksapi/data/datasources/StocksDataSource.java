package com.nicolas.stocksapi.data.datasources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nicolas.stocksapi.data.mappers.StockMapper;
import com.nicolas.stocksapi.data.utils.ResultToList;
import com.nicolas.stocksapi.domain.entities.StockEntity;

import io.vavr.control.Either;

public class StocksDataSource {
    private final String databaseUrl = "localhost:5432";
	private final String databaseName = "stocks_db";
	private final String username = "postgres";
	private final String password = "postgres";

    private Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(String.format("jdbc:postgresql://%s/%s", databaseUrl, databaseName), username, password);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return conn;
	}

    public Either<Exception, StockEntity[]> getStocksList() {
        Connection conn = connect();
        if (conn == null) return Either.left(new Exception("Could not connect to server"));

        List<StockEntity> result = new ArrayList<StockEntity>();

        try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM stocks LIMIT 50");
			
			List<Map<String, Object>> list = ResultToList.convert(rs);
            for (Map<String,Object> map : list) {
                result.add(StockMapper.fromMap(map));
            }

		} catch (SQLException e) {
			return Either.left(e);
		}

		try {
			conn.close();
		} catch(Exception e) {}

		StockEntity[] modelArray = new StockEntity[result.size()];
		return Either.right(result.toArray(modelArray));
    }
}
