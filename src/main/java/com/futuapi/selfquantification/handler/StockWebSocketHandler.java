package com.futuapi.selfquantification.handler;

import com.alibaba.fastjson2.JSON;
import com.futuapi.selfquantification.enums.ErrorCodeEnum;
import com.futuapi.selfquantification.event.StockQuoteEvent;
import com.futuapi.selfquantification.service.QueryStockInfoDetailService;
import com.futuapi.selfquantification.vo.ApiResponse;
import com.futuapi.selfquantification.vo.request.QueryStockQuoteRequest;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author 周欣
 * @date 2025/3/31 17:45
 */
@Component
@Slf4j
public class StockWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Resource
    private QueryStockInfoDetailService queryStockInfoDetailService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            log.info("StockWebSocketHandler收到WebSocket消息: {}", message.getPayload());

            // 解析前端发来的订阅请求
            String payload = message.getPayload();
            QueryStockQuoteRequest request = JSON.parseObject(payload, QueryStockQuoteRequest.class);

            log.info("解析后的请求对象: {}", JSON.toJSONString(request));

            // 参数校验
            if (StringUtils.isBlank(request.getStockCode()) || Objects.isNull(request.getMarketId())) {
                session.sendMessage(new TextMessage(JSON.toJSONString(ApiResponse.error(ErrorCodeEnum.PARAM_ERROR))));
                return;
            }

            // 调用服务订阅股票
            queryStockInfoDetailService.queryStockInfoDetail(request.getStockCode(), request.getMarketId());
            // session.sendMessage(new TextMessage(JSON.toJSONString(ApiResponse.success())));
        } catch (Exception e) {
            log.error("WebSocket处理消息异常, message={}", message.getPayload(), e);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("新的WebSocket连接建立: {}", session.getId());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("WebSocket连接关闭: {}", session.getId());
        sessions.remove(session);
    }

    public void broadcastStockPrice(String message) {
        TextMessage textMessage = new TextMessage(message);
        sessions.forEach(session -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(textMessage);
                }
            } catch (Exception e) {
                log.error("发送股票数据失败", e);
            }
        });
    }

    @EventListener
    public void handleStockQuoteEvent(StockQuoteEvent event) {
        broadcastStockPrice(event.getStockData());
    }
}