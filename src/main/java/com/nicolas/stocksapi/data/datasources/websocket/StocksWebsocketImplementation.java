package com.nicolas.stocksapi.data.datasources.websocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import io.vavr.control.Either;

public class StocksWebsocketImplementation implements IStocksWebsocket {

    @Override
    public Either<Exception, Boolean> notifySockets() {
        var handlerList = StocksWebsocketConfigurer.getHandlerList();
        
        for (StocksWebSocketHandler handler : handlerList) {
            handler.notifyAllSessions();
        }

        return Either.right(true);
    }

    @Configuration
    @EnableWebSocket
    public class StocksWebsocketConfigurer implements WebSocketConfigurer {

        private static List<StocksWebSocketHandler> handlerList = new ArrayList<>(); 

        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(getHandler(), "/listen").setAllowedOrigins("*");
        }

        private TextWebSocketHandler getHandler() { 
            var handler = new StocksWebSocketHandler(); 
            handlerList.add(handler);
            return handler;
        }

        public static List<StocksWebSocketHandler> getHandlerList() { return handlerList; }
    }
}
