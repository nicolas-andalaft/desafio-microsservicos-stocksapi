package com.nicolas.stocksapi.data.datasources;

public interface IStocksListener {
    public void listenToNotifications(); 
    public void onNotification(String payload);
}
