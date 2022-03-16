package com.nicolas.stocksapi.domain.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class StockEntity {
    protected Long id;
    protected String stockSymbol;
    protected String stockName;
    protected BigDecimal askMin;
    protected BigDecimal askMax;
    protected BigDecimal bidMin;
    protected BigDecimal bidMax;
    protected Timestamp createdOn;
    protected Timestamp updatedOn;

    public void setId(Long id) { this.id = id; }
    public void setStockSymbol(String stockSymbol) { this.stockSymbol = stockSymbol; }
    public void setStockName(String stockName) { this.stockName = stockName; }
    public void setAskMin(BigDecimal askMin) { this.askMin = askMin; }
    public void setAskMax(BigDecimal askMax) { this.askMax = askMax; }
    public void setBidMin(BigDecimal bidMin) { this.bidMin = bidMin; }
    public void setBidMax(BigDecimal bidMax) { this.bidMax = bidMax; }
    public void setCreatedOn(Timestamp createdOn) { this.createdOn = createdOn; }
    public void setUpdatedOn(Timestamp updatedOn) { this.updatedOn = updatedOn; }

    public Long getId() { return this.id; }
    public String getStockSymbol() { return this.stockSymbol; }
    public String getStockName() { return this.stockName; }
    public BigDecimal getAskMin() { return this.askMin; }
    public BigDecimal getAskMax() { return this.askMax; }
    public BigDecimal getBidMin() { return this.bidMin; }
    public BigDecimal getBidMax() { return this.bidMax; }
    public Timestamp getCreatedOn() { return this.createdOn; }
    public Timestamp getUpdatedOn() { return this.updatedOn; }
}
