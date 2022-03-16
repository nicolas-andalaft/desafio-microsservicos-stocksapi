package com.nicolas.stocksapi.presenter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.nicolas.stocksapi.core.BidAskHelper;
import com.nicolas.stocksapi.core.IModel;
import com.nicolas.stocksapi.core.NoParams;
import com.nicolas.stocksapi.data.datasources.*;
import com.nicolas.stocksapi.data.datasources.postgre.PostgreStocksDataSource;
import com.nicolas.stocksapi.data.datasources.postgre.PostgreStocksListener;
import com.nicolas.stocksapi.data.datasources.websocket.IStocksWebsocket;
import com.nicolas.stocksapi.data.datasources.websocket.StocksWebsocketConfig;
import com.nicolas.stocksapi.data.datasources.websocket.StocksWebsocket;
import com.nicolas.stocksapi.data.models.StockModel;
import com.nicolas.stocksapi.data.repositories.StocksRepository;
import com.nicolas.stocksapi.domain.entities.StockEntity;
import com.nicolas.stocksapi.domain.repositories.IStocksRepository;
import com.nicolas.stocksapi.domain.usecases.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.vavr.control.Either;

@RestController
@CrossOrigin
class StocksAPI {

	// Datasource interfaces
	private IStocksDatasource stocksDatasource;

	// Repository interfaces
	private IStocksRepository stocksRepository;

	// Websocket objects
	private StocksWebsocketConfig stocksWebsocketConfig;
	private IStocksWebsocket stocksWebsocket;

	// Listener interfaces
	private IStocksListener stocksListener;

	// Usecases
	private GetStocksListUsecase getStocksListUsecase;
	private GetStockUsecase getStockUsecase;
	private GetRandomStocksUsecase getRandomStocksUsecase;
	private CheckBidAskUsecase checkBidAskUsecase;
	private UpdateStockValuesUsecase updateStockValuesUsecase;
	private GetStockHistoryUsecase getStockHistoryUsecase;
	private GenerateRandomStockHistoryUsecase generateRandomStockHistoryUsecase;

	private NoParams noParams;
	
	public StocksAPI() {
		// Datasources
		stocksDatasource = new PostgreStocksDataSource();
		
		// Repositories
		stocksRepository = new StocksRepository(stocksDatasource);
		
		// Websockets
		stocksWebsocketConfig = new StocksWebsocketConfig();
		stocksWebsocket = new StocksWebsocket(stocksWebsocketConfig);
		
		// Listeners
		stocksListener = new PostgreStocksListener(stocksWebsocket);
		
		// Usecases
		getStocksListUsecase = new GetStocksListUsecase(stocksRepository);
		getStockUsecase = new GetStockUsecase(stocksRepository);
		getRandomStocksUsecase = new GetRandomStocksUsecase(stocksRepository);
		checkBidAskUsecase = new CheckBidAskUsecase(stocksRepository);
		updateStockValuesUsecase = new UpdateStockValuesUsecase(stocksRepository);
		getStockHistoryUsecase = new GetStockHistoryUsecase(stocksRepository);
		generateRandomStockHistoryUsecase = new GenerateRandomStockHistoryUsecase(stocksRepository);

		noParams = new NoParams();

		stocksListener.listenToNotifications();
	}

	private static final String WRONG_PARAMETER_MESSAGE = "Parameter in wrong format";
	private static final StockModel stockModel = new StockModel();

	private static final String GET_STOCKS_LIST = "/stocks";
	private static final String GET_STOCK_BY_ID = "/stocks/{id}";
	private static final String GET_RANDOM_STOCKS = "/stocks/random/{qty}";
	private static final String CHECK_BID_ASK = "/stocks/{id}/check/{type}/{value}";
	private static final String UPDATE_BID_ASK = "/stocks/{id}/update";
	private static final String GET_STOCK_HISTORY = "/stocks/{id}/history";
	private static final String GENERATE_RANDOM_STOCK_HISTORY = "/__generate_stock_history__";
	
	@GetMapping("/")
	public String root() { return "StocksAPI"; }

	@GetMapping("/error")
	public String error() { return "Endpoint doesn't exist"; }

	@GetMapping(GET_STOCKS_LIST)
	public ResponseEntity<Object> getStocksList() {
		var result = getStocksListUsecase.call(noParams);

		return returnList(result, stockModel);
	}

	@GetMapping(GET_STOCK_BY_ID)
	public ResponseEntity<Object> getStockById(@PathVariable String id) {		
		Long idStock;
		try {
			idStock = Long.parseLong(id);
		} catch (Exception e) {
			return returnBadRequest(Either.left(WRONG_PARAMETER_MESSAGE));
		}

		var stock = new StockEntity();
		stock.setId(idStock);
		var result = getStockUsecase.call(stock);

		return returnEntity(result, stockModel);
	}

	@GetMapping(GET_RANDOM_STOCKS)
	public ResponseEntity<Object> getRandomStocks(@PathVariable String qty) {		
		int stocksQty;
		try {
			stocksQty = Integer.parseInt(qty);
		} catch (Exception e) {
			return returnBadRequest(Either.left(WRONG_PARAMETER_MESSAGE));
		}

		var result = getRandomStocksUsecase.call(stocksQty);

		return returnList(result, stockModel);
	}

	@GetMapping(CHECK_BID_ASK)
	public ResponseEntity<Object> checkBidAsk(@PathVariable String id, @PathVariable String type, @PathVariable String value) {
		var bidAsk = new BidAskHelper();
		
		try {
			bidAsk.id_stock = Long.valueOf(id);

			bidAsk.type = Integer.parseInt(type);

			var bidAskValue = Double.parseDouble(value);
			bidAsk.value = BigDecimal.valueOf(bidAskValue);

			if (bidAsk.type != 0 && bidAsk.type != 1) throw new IllegalArgumentException();
		}
		catch (Exception e) {
			return returnBadRequest(Either.left(WRONG_PARAMETER_MESSAGE));
		}

		var	result = checkBidAskUsecase.call(bidAsk);

		return returnEntity(result, stockModel);
	}

	@PostMapping(UPDATE_BID_ASK)
	public ResponseEntity<Object> updateBidAsk(@RequestBody Map<String, Object> body) {
		
		var stock = StockModel.fromMap(body);
		
		var result = updateStockValuesUsecase.call(stock);

		return returnEntity(result, stockModel);
	}

	@GetMapping(GET_STOCK_HISTORY)
	public ResponseEntity<Object> getStockHistory(@PathVariable String id) {
		var stock = new StockEntity();
		
		try {
			stock.setId(Long.valueOf(id));
		}
		catch (Exception e) {
			return returnBadRequest(Either.left(WRONG_PARAMETER_MESSAGE));
		}

		var	result = getStockHistoryUsecase.call(stock);

		return returnList(result, stockModel);
	}

	@GetMapping(GENERATE_RANDOM_STOCK_HISTORY)
	public ResponseEntity<Object> generateRandomStockHistory() {
		var result = generateRandomStockHistoryUsecase.call(noParams);

		return returnObject(result);
	}

	private ResponseEntity<Object> returnObject(Either<Exception, ?> result) {
		if (result.isLeft())
			return returnServerError(result);

		return ResponseEntity.ok(result.get());
	}

	private <A> ResponseEntity<Object> returnEntity(Either<Exception, A> result, IModel<A> model) {
		if (result.isLeft())
			return returnServerError(result);

		return ResponseEntity.ok(model.toMap(result.get()));
	}

	private <A> ResponseEntity<Object> returnList(Either<Exception, List<A>> result, IModel<A> model) {
		if (result.isLeft())
			return returnServerError(result);

		var body = result.get().stream().map(model::toMap);
		return ResponseEntity.ok(body);
	}
	
	private ResponseEntity<Object> returnServerError(Either<?,?> result) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getLeft());
	}

	private ResponseEntity<Object> returnBadRequest(Either<?,?> result) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getLeft());
	}

	
}