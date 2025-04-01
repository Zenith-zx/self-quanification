package com.futuapi.selfquantification.controller;

import com.alibaba.fastjson2.JSON;
import com.futuapi.selfquantification.enums.ErrorCodeEnum;
import com.futuapi.selfquantification.handler.StockWebSocketHandler;
import com.futuapi.selfquantification.service.QueryStockInfoDetailService;
import com.futuapi.selfquantification.vo.ApiResponse;
import com.futuapi.selfquantification.vo.StockInfoDetailVO;
import com.futuapi.selfquantification.vo.request.QueryStockQuoteRequest;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author 周欣
 * @date 2025/3/31 15:39
 */
//@RestController
//@RequestMapping("/api/stock/stockMarket")
@Slf4j
@Controller
public class StockMarketController  extends TextWebSocketHandler {

//    @Resource
//    private QueryStockInfoDetailService queryStockInfoDetailService;
//
//    @Resource
//    private StockWebSocketHandler stockWebSocketHandler;
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
//        try {
//            // 添加日志
//            log.info("收到WebSocket消息: {}", message.getPayload());
//
//            // 解析前端发来的订阅请求
//            String payload = message.getPayload();
//            QueryStockQuoteRequest request = JSON.parseObject(payload, QueryStockQuoteRequest.class);
//
//            // 参数校验
//            if (StringUtils.isBlank(request.getStockCode()) || Objects.isNull(request.getMarketId())) {
//                session.sendMessage(new TextMessage(JSON.toJSONString(ApiResponse.error(ErrorCodeEnum.PARAM_ERROR))));
//                return;
//            }
//
//            // 调用服务订阅股票
//            queryStockInfoDetailService.queryStockInfoDetail(request.getStockCode(), request.getMarketId());
//            // session.sendMessage(new TextMessage(JSON.toJSONString(ApiResponse.success())));
//        } catch (Exception e) {
//            log.error("WebSocket处理消息异常, message={}", message.getPayload(), e);
//        }
//    }

//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) {
//        log.info("新的WebSocket连接建立: {}", session.getId());
//        stockWebSocketHandler.addSession(session);  // 添加新的会话
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//        log.info("WebSocket连接关闭: {}", session.getId());
//        stockWebSocketHandler.removeSession(session);  // 移除关闭的会话
//    }

//    @RequestMapping(value = "/stock/quote", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public ApiResponse<Void> queryStockQuote(QueryStockQuoteRequest request) {
//        if (Objects.isNull(request)) {
//            return ApiResponse.error(ErrorCodeEnum.PARAM_ERROR);
//        }
//        if (StringUtils.isBlank(request.getStockCode()) || Objects.isNull(request.getMarketId())) {
//            return ApiResponse.error(ErrorCodeEnum.PARAM_ERROR);
//        }
//        try {
//            queryStockInfoDetailService.queryStockInfoDetail(request.getStockCode(), request.getMarketId());
//            return ApiResponse.success();
//        } catch (Exception e) {
//            log.error("StockMarketController QueryStockQuote error, request = {},", JSON.toJSONString(request), e);
//        }
//        return ApiResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
//    }

}
