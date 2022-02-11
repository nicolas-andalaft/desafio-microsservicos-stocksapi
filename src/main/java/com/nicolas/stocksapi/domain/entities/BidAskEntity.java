package com.nicolas.stocksapi.domain.entities;

import java.math.BigDecimal;

public class BidAskEntity {
    public Long id_stock;
    public BigDecimal value;
    public Integer type;

    public BidAskEntity() {}
}
