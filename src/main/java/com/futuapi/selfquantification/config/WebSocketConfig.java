package com.futuapi.selfquantification.config;

import com.futuapi.selfquantification.controller.StockMarketController;
import com.futuapi.selfquantification.handler.StockHistoryKLineHandler;
import com.futuapi.selfquantification.handler.StockWebSocketHandler;
import jakarta.annotation.Resource;
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

    @Resource
    private StockWebSocketHandler stockWebSocketHandler;

    @Resource
    private StockHistoryKLineHandler stockHistoryKLineHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        /**
         * 实时查询股票报价
         */
        registry.addHandler(stockWebSocketHandler, "/api/stock/stockMarket/stock/quote")
            .setAllowedOrigins("*");

        /**
         * 查询股票历史K线数据
         */
        registry.addHandler(stockHistoryKLineHandler, "/api/stock/stockMarket/stock/history/kline")
            .setAllowedOrigins("*");
    }
}
