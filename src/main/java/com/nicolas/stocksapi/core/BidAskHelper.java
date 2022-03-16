package com.nicolas.stocksapi.core;

import java.math.BigDecimal;

public class BidAskHelper {
    private Long idStock;
    private BigDecimal value;
    private Integer type;

    public void setIdStock(Long idStock) { this.idStock = idStock; }
    public void setValue(BigDecimal value) { this.value = value; }
    public void setType(Integer type) { this.type = type; }

    public Long getIdStock() { return this.idStock; }
    public BigDecimal getValue() { return this.value; }
    public Integer getType() { return this.type; }
}
