package com.nicolas.stocksapi.data.datasources.websocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import io.vavr.control.Either;

public class StocksWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> webSocketSessions = new ArrayList<>();
    
    @Override 
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessions.add(session);
    }

    @Override 
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessions.remove(session);
    }

    public Either<Exception, Boolean> notifyAllSessions() {
        try {
            for(WebSocketSession session : webSocketSessions) {
                session.sendMessage(new TextMessage("teste"));
            }

            return Either.right(true);
        }
        catch (Exception e) {
            return Either.left(e);
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        session.sendMessage(message);
    }
}
