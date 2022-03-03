package com.nicolas.stocksapi.data.datasources.websocket;

import io.vavr.control.Either;

public interface IStocksWebsocket {
    public Either<Exception, Boolean> notifySockets(); 
}
