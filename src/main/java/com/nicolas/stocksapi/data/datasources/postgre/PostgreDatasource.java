package com.nicolas.stocksapi.data.datasources.postgre;

import java.util.Map;

import javax.sql.DataSource;

import com.nicolas.stocksapi.data.utils.ResultConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import io.vavr.collection.List;
import io.vavr.control.Either;

@Configuration
public abstract class PostgreDatasource {
	private DataSource privateDatasource;
	protected String tableName;

	protected PostgreDatasource(String tableName) {
		this.tableName = tableName;
	}

	@Bean
	public DataSource dataSource() {
		if (privateDatasource != null)
			return privateDatasource;

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setUrl("jdbc:postgresql://localhost:5432/stocks_db");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		privateDatasource = dataSource;

		return privateDatasource;
	}

	protected Either<Exception, List<Map<String, Object>>> execute(String sqlString) {
        Either<Exception, List<Map<String, Object>>> result;
		var datasource = dataSource();
		        
		try (var conn = datasource.getConnection(); var statement = conn.createStatement()) {

			var rs = statement.executeQuery(sqlString);
			var response = ResultConverter.toMapList(rs);
			result = Either.right(response);

		} catch (Exception e) {
			result = Either.left(e);
		}

		return result;
	}
}
