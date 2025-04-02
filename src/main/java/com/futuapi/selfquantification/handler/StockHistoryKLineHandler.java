package com.futuapi.selfquantification.handler;

import com.alibaba.fastjson2.JSON;
import com.futuapi.selfquantification.enums.ErrorCodeEnum;
import com.futuapi.selfquantification.service.QueryStockHistoryKLineService;
import com.futuapi.selfquantification.vo.ApiResponse;
import com.futuapi.selfquantification.vo.request.QueryStockHistoryKLineRequest;
import com.futuapi.selfquantification.vo.request.QueryStockQuoteRequest;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author 周欣
 * @date 2025/4/2 14:29
 */
@Component
@Slf4j
public class StockHistoryKLineHandler extends TextWebSocketHandler {

    @Resource
    private QueryStockHistoryKLineService queryStockHistoryKLineService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            log.info("StockHistoryKLineHandler收到WebSocket消息: {}", message.getPayload());

            // 解析前端发来的订阅请求
            String payload = message.getPayload();
            QueryStockHistoryKLineRequest request = JSON.parseObject(payload, QueryStockHistoryKLineRequest.class);

            log.info("StockHistoryKLineHandler解析后的请求对象: {}", JSON.toJSONString(request));

            // 参数校验
//            if (StringUtils.isBlank(request.getStockCode()) || Objects.isNull(request.getMarketId())) {
//                session.sendMessage(new TextMessage(JSON.toJSONString(ApiResponse.error(ErrorCodeEnum.PARAM_ERROR))));
//                return;
//            }

            // 调用服务订阅股票
            // queryStockHistoryKLineService.queryStockHistoryKLine(request.getStockCode(), request.getMarketId());
            // session.sendMessage(new TextMessage(JSON.toJSONString(ApiResponse.success())));
        } catch (Exception e) {
            log.error("WebSocket处理消息异常, message={}", message.getPayload(), e);
        }
    }



}
