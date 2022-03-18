package com.nicolas.stocksapi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nicolas.stocksapi.data.datasources.IStocksDatasource;
import com.nicolas.stocksapi.data.datasources.postgre.PostgreStocksDataSource;
import com.nicolas.stocksapi.data.repositories.StocksRepository;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;
import com.nicolas.stocksapi.domain.usecases.GetStocksListUsecase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StocksapiApplicationTests {

	private static IStocksDatasource stocksDatasource;
	private static IStocksRepository stocksRepository;

	@BeforeAll
	private static void init() {
		stocksDatasource = new PostgreStocksDataSource();
		stocksRepository = new StocksRepository(stocksDatasource);
	}

	@Test
	void shouldGetStocks() {
		var getStocksListUsecase = new GetStocksListUsecase(stocksRepository);
		var result = getStocksListUsecase.call(null);

		assertTrue(result.isRight());
	}

}
