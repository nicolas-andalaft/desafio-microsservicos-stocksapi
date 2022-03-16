package com.nicolas.stocksapi.data.datasources.websocket;

public class StocksWebsocket implements IStocksWebsocket {

    private StocksWebsocketConfig stocksWebsocketConfig;

    public StocksWebsocket(StocksWebsocketConfig stocksWebsocketConfig) {
        this.stocksWebsocketConfig = stocksWebsocketConfig;
    }

    @Override
    public String notifyClients() {
        stocksWebsocketConfig.notifyClients();
        return "update";
    } 
}