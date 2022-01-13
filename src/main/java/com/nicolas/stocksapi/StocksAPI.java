package com.nicolas.stocksapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
class StocksAPI {
	private final String databaseUrl = "localhost:5432";
	private final String databaseName = "stocks_db";
	private final String username = "postgres";
	private final String password = "postgres";

	@GetMapping("/stocks")
	public List<Map<String, ?>> getStocks() {
		Connection conn = connect();
		List<Map<String, ?>> result = null;

		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM stocks");
			
			result = toList(rs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try {
			conn.close();
		} catch(Exception e) {}

		return result;
	}

	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(String.format("jdbc:postgresql://%s/%s", databaseUrl, databaseName), username, password);
			System.out.println("Connected to the PostgreSQL server successfully.");
			
		} catch (SQLException e) {
			System.out.println("SQLEXCEPTION: " + e.getMessage());
		}

		return conn;
	}

	private List<Map<String, ?>> toList(ResultSet result) {
		List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
		try {
			ResultSetMetaData metadata = result.getMetaData();
			int columnCount = metadata.getColumnCount();

			while (result.next()) {
				Map<String, Object> entry = new HashMap<String, Object>();

				for (int i = 1; i <= columnCount; i++) {
					entry.put(metadata.getColumnLabel(i), result.getObject(i));
				}

				list.add(entry);
			}
		} catch (Exception e) {}

		return list;
	}
}