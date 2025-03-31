package com.futuapi.selfquantification.config;

import com.futuapi.selfquantification.handler.StockWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author 周欣
 * @date 2025/3/31 19:25
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(stockWebSocketHandler(), "/api/stock")
            .setAllowedOrigins("*");
    }

    @Bean
    public StockWebSocketHandler stockWebSocketHandler() {
        return new StockWebSocketHandler();
    }
}
