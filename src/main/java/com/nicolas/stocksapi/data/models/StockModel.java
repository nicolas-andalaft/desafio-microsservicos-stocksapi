package com.nicolas.stocksapi.data.models;

import java.util.Map;

import com.nicolas.stocksapi.core.IModel;
import com.nicolas.stocksapi.data.utils.MapGetter;
import com.nicolas.stocksapi.domain.entities.StockEntity;

import io.vavr.collection.HashMap;

public class StockModel extends StockEntity implements IModel<StockEntity> {

  public StockModel() {}

  public StockModel(StockEntity stock) {
    this.id = stock.getId();
    this.stockSymbol = stock.getStockSymbol();
    this.stockName = stock.getStockName();
    this.askMin = stock.getAskMin();
    this.askMax = stock.getAskMax();
    this.bidMin = stock.getBidMin();
    this.bidMax = stock.getBidMax();
    this.createdOn = stock.getCreatedOn();
    this.updatedOn = stock.getUpdatedOn();
  } 

  public static StockModel fromMap(Map<String, Object> map) {
    StockModel stock = new StockModel();

    stock.id = MapGetter.getLong(map, "id");
    stock.stockSymbol = MapGetter.getString(map, "stock_symbol");
    stock.stockName = MapGetter.getString(map, "stock_name");
    stock.askMin = MapGetter.getBigDecimal(map, "ask_min");
    stock.askMax = MapGetter.getBigDecimal(map, "ask_max");
    stock.bidMin = MapGetter.getBigDecimal(map, "bid_min");
    stock.bidMax = MapGetter.getBigDecimal(map, "bid_max");
    stock.createdOn = MapGetter.getTimestamp(map, "created_on");
    stock.updatedOn = MapGetter.getTimestamp(map, "updated_on");
    
    return stock;
  }

  @Override
  public Map<String, Object> toMap() {
    return toMap(this);    
  }

  @Override
  public Map<String, Object> toMap(StockEntity stock) {
    HashMap<String, Object> map = HashMap.of(
      "id", stock.getId(),
      "stock_symbol", stock.getStockSymbol(),
      "stock_name", stock.getStockName(),
      "ask_min", stock.getAskMin(),
      "ask_max", stock.getAskMax(),
      "bid_min", stock.getBidMin(),
      "bid_max", stock.getBidMax(),
      "created_on", stock.getCreatedOn(),
      "updated_on", stock.getUpdatedOn()
    );
    
    return map.toJavaMap();
  }
}