package com.nicolas.stocksapi.domain.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class StockEntity {
    public Long id;
    public String stock_symbol;
    public String stock_name;
    public BigDecimal ask_min;
    public BigDecimal ask_max;
    public BigDecimal bid_min;
    public BigDecimal bid_max;
    public Timestamp created_on;
    public Timestamp updated_on;

    public StockEntity() {}
}
