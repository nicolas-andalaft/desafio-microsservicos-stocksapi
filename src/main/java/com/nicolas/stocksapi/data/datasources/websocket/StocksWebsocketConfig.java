package com.nicolas.stocksapi.data.datasources.websocket;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Component
public class StocksWebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private SimpMessagingTemplate privateMessagingTemplate;
    private static SimpMessagingTemplate messagingTemplate;

    private static void initializeMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        StocksWebsocketConfig.messagingTemplate = messagingTemplate;
    }

    @PostConstruct
    private void initializeMessagingTemplate() {
        StocksWebsocketConfig.initializeMessagingTemplate(privateMessagingTemplate);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stocks/listen").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/messages");
    }

    public void notifyClients() {
        messagingTemplate.convertAndSend("/messages", "");
    }
}
