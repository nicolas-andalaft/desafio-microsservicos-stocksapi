package com.nicolas.stocksapi.data.mappers;

import java.util.Map;
import com.nicolas.stocksapi.data.utils.MapGetter;
import com.nicolas.stocksapi.domain.entities.StockEntity;

public class StockMapper {
public static StockEntity fromMap(Map<String, ?> map) {
        StockEntity stock = new StockEntity(
            MapGetter.getLong(map, "id"),
            MapGetter.getLong(map, "market_cap"),
            MapGetter.getString(map, "stocks_symbol"),
            MapGetter.getString(map, "stocks_name"),
            MapGetter.getFloat(map, "ask_min"),
            MapGetter.getFloat(map, "ask_max"),
            MapGetter.getFloat(map, "bid_min"),
            MapGetter.getFloat(map, "bid_max"),
            MapGetter.getLocalDateTime(map, "created_on"),
            MapGetter.getLocalDateTime(map, "updated_on")
        );
      return stock;
    }    
}
