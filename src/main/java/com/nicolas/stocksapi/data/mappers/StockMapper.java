package com.nicolas.stocksapi.data.mappers;

import java.util.HashMap;
import java.util.Map;
import com.nicolas.stocksapi.data.utils.MapGetter;
import com.nicolas.stocksapi.domain.entities.StockEntity;

public class StockMapper {
  private final static String id = "id";
  private final static String marketCap = "market_cap";
  private final static String stockSymbol = "stock_symbol";
  private final static String stockName = "stock_name";
  private final static String askMin = "ask_min";
  private final static String askMax = "bid_max";
  private final static String bidMin = "bid_min";
  private final static String bidMax = "bid_max";
  private final static String createdOn = "created_on";
  private final static String updatedOn = "updated_on";

  public static StockEntity fromMap(Map<String, ?> map) {
    StockEntity stock = new StockEntity(
      MapGetter.getLong(map, id),
      MapGetter.getLong(map, marketCap),
      MapGetter.getString(map, stockSymbol),
      MapGetter.getString(map, stockName),
      MapGetter.getFloat(map, askMin),
      MapGetter.getFloat(map, askMax),
      MapGetter.getFloat(map, bidMin),
      MapGetter.getFloat(map, bidMax),
      MapGetter.getLocalDateTime(map, createdOn),
      MapGetter.getLocalDateTime(map, updatedOn)
    );
    return stock;
  }   
    
  public static Map<String, Object> toMap(StockEntity stock) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put(id, stock.id);
    map.put(marketCap, stock.marketCap);
    map.put(stockSymbol, stock.stockSymbol);
    map.put(stockName, stock.stockName);
    map.put(askMin, stock.askMin);
    map.put(askMax, stock.askMax);
    map.put(bidMin, stock.bidMin);
    map.put(bidMax, stock.bidMax);
    map.put(createdOn, stock.createdOn);
    map.put(updatedOn, stock.updatedOn);
    
    return map;
  }
}
