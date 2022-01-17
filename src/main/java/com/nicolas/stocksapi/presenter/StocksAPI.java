package com.nicolas.stocksapi.presenter;

import com.nicolas.stocksapi.core.NoParams;
import com.nicolas.stocksapi.data.repositories.StocksRepositoryImplementation;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.StocksRepository;
import com.nicolas.stocksapi.domain.usecases.GetStocksUsecase;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.vavr.control.Either;

@RestController
@CrossOrigin
class StocksAPI {
	private StocksRepository stocksRepository;
	private GetStocksUsecase getStocksUsecase;

	public StocksAPI() {
		stocksRepository = new StocksRepositoryImplementation();
		getStocksUsecase = new GetStocksUsecase(stocksRepository);
	}

	@GetMapping("/")
	public void root() {}

	@GetMapping("/stocks")
	public Object[] stocks() {
		Either<Exception, StockEntity[]> result = getStocksUsecase.call(new NoParams());
		if (result.isLeft()) 
			return new Object[]{result.getLeft().toString()};
		else
			return result.get();
	}
}