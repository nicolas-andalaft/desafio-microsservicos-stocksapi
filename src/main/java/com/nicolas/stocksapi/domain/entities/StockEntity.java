package com.nicolas.stocksapi.domain.entities;

import java.time.LocalDateTime;

public class StockEntity {
    public Long id;
    public Long marketCap;
    public String stockSymbol;
    public String stockName;
    public Float askMin;
    public Float askMax;
    public Float bidMin;
    public Float bidMax;
    public LocalDateTime createdOn;
    public LocalDateTime updatedOn;

    public StockEntity(
        Long id,
        Long marketCap,
        String stockSymbol,
        String stockName,
        Float askMin,
        Float askMax,
        Float bidMin,
        Float bidMax,
        LocalDateTime createdOn,
        LocalDateTime updatedOn
    ) {
        this.id = id;
        this.marketCap = marketCap;
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
        this.askMin = askMin;
        this.askMax = askMax;
        this.bidMin = bidMin;
        this.bidMax = bidMax;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }
}
