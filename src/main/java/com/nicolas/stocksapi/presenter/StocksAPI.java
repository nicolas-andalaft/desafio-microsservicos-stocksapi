package com.nicolas.stocksapi.presenter;

import java.math.BigDecimal;

import com.nicolas.stocksapi.core.NoParams;
import com.nicolas.stocksapi.data.datasources.IStocksDatasource;
import com.nicolas.stocksapi.data.datasources.postgre.PostgreStocksDataSource;
import com.nicolas.stocksapi.data.repositories.StocksRepository;
import com.nicolas.stocksapi.domain.entities.BidAskEntity;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;
import com.nicolas.stocksapi.domain.usecases.GetRandomStocksUsecase;
import com.nicolas.stocksapi.domain.usecases.GetStockUsecase;
import com.nicolas.stocksapi.domain.usecases.GetStocksListUsecase;
import com.nicolas.stocksapi.domain.usecases.UpdateBidAskUsecase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.vavr.control.Either;

@RestController
@CrossOrigin
class StocksAPI {
	// Datasource interfaces
	private IStocksDatasource stocksDatasource;

	// Repository interfaces
	private IStocksRepository stocksRepository;

	// Usecases
	private GetStocksListUsecase getStocksListUsecase;
	private GetStockUsecase getStockUsecase;
	private GetRandomStocksUsecase getRandomStocksUsecase;
	private UpdateBidAskUsecase updateBidAskUsecase;

	private NoParams noParams;
	
	public StocksAPI() {
		stocksDatasource = new PostgreStocksDataSource();

		stocksRepository = new StocksRepository(stocksDatasource);

		getStocksListUsecase = new GetStocksListUsecase(stocksRepository);
		getStockUsecase = new GetStockUsecase(stocksRepository);
		getRandomStocksUsecase = new GetRandomStocksUsecase(stocksRepository);
		updateBidAskUsecase = new UpdateBidAskUsecase(stocksRepository);

		noParams = new NoParams();
	}

	private final String getStocksList = "/stocks{id}";
	private final String getRandomStocks = "/stocks/random/{qty}";
	private final String updateBidAsk = "/stocks/{id}/update/{type}/{value}";
	
	@GetMapping("/")
	public String root() { return "StocksAPI"; }

	@GetMapping("/error")
	public String error() { return "Endpoint doesn't exist"; }
	
	@GetMapping("/stocks")
	public ResponseEntity<?> getStocksList() {
		var result = getStocksListUsecase.call(noParams);

		if (result.isLeft()) 
			return returnServerError(result);
		else
			return returnOk(result);
	}

	@GetMapping(getStocksList)
	public ResponseEntity<?> getStockById(@PathVariable String id) {		
		Long id_stock;
		try {
			id_stock = Long.parseLong(id);
		} catch (Exception e) {
			return returnBadRequest(Either.left("Parameter in wrong format"));
		}

		var stock = new StockEntity();
		stock.id = id_stock;
		var result = getStockUsecase.call(stock);

		if (result.isLeft()) 
			return returnServerError(result);
		else
			return returnOk(result);
	}

	@GetMapping(getRandomStocks)
	public ResponseEntity<?> getRandomStocks(@PathVariable String qty) {		
		int stocksQty;
		try {
			stocksQty = Integer.parseInt(qty);
		} catch (Exception e) {
			return returnBadRequest(Either.left("Parameter in wrong format"));
		}

		var result = getRandomStocksUsecase.call(stocksQty);

		if (result.isLeft()) 
			return returnServerError(result);
		else
			return returnOk(result);
	}

	@GetMapping(updateBidAsk)
	public ResponseEntity<?> updateBidAsk(@PathVariable String id, @PathVariable String type, @PathVariable String value) {
		var bidAsk = new BidAskEntity();
		
		try {
			bidAsk.id_stock = Long.valueOf(id);

			bidAsk.type = Integer.parseInt(type);

			var bidAskValue = Double.parseDouble(value);
			bidAsk.value = BigDecimal.valueOf(bidAskValue);

			if (bidAsk.type != 0 && bidAsk.type != 1) throw new Exception();
		}
		catch (Exception e) {
			return returnBadRequest(Either.left("Parameter in wrong format"));
		}

		var	result = updateBidAskUsecase.call(bidAsk);

		if (result.isLeft()) 
			return returnServerError(result);
		else
			return returnOk(result);
	}

	private ResponseEntity<?> returnServerError(Either<?,?> result) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getLeft());
	}

	private ResponseEntity<?> returnBadRequest(Either<?,?> result) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getLeft());
	}

	private ResponseEntity<?> returnOk(Either<?,?> result) {
		return ResponseEntity.status(HttpStatus.OK).body(result.get());
	}
}