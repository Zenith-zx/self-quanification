package com.futuapi.selfquantification.service;

import com.alibaba.fastjson2.JSON;
import com.futu.openapi.FTAPI_Conn;
import com.futu.openapi.FTAPI_Conn_Qot;
import com.futu.openapi.FTSPI_Conn;
import com.futu.openapi.FTSPI_Qot;
import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotCommon.Security;
import com.futu.openapi.pb.QotSub;
import com.futu.openapi.pb.QotSub.C2S;
import com.futu.openapi.pb.QotSub.Request;
import com.futu.openapi.pb.QotUpdateBasicQot.Response;
import com.futuapi.selfquantification.enums.ErrorCodeEnum;
import com.futuapi.selfquantification.event.StockQuoteEvent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author 周欣
 * @date 2025/3/31 19:22
 */
@Service
@Slf4j
public class QueryStockInfoDetailService implements FTSPI_Qot, FTSPI_Conn {

    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Resource
    private FTAPI_Conn_Qot qot;

    @PostConstruct
    public void init() {
        qot.setConnSpi(this);
        qot.setQotSpi(this);
    }




    @Override
    public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
        if (errCode != ErrorCodeEnum.SUCCESS.getCode()) {
            log.error("QueryStockInfoDetailService onInitConnect failed, errorCode = {}", errCode);
            return;
        }
        log.info("富途连接初始化: ret={} desc={} connID={}", errCode, desc, client.getConnectID());
    }

    private Request buildQotSubRequest(String stockCode, int marketId) {
        Security security = QotCommon.Security.newBuilder().setMarket(marketId).setCode(stockCode).build();
        C2S c2s = QotSub.C2S.newBuilder().addSecurityList(security).addSubTypeList(QotCommon.SubType.SubType_Basic_VALUE)
            .setIsSubOrUnSub(true).setIsRegOrUnRegPush(true).build();
        return QotSub.Request.newBuilder().setC2S(c2s).build();
    }

    public void queryStockInfoDetail(String stockCode, int marketId) {
        Request request = buildQotSubRequest(stockCode, marketId);
        int seqNo = qot.sub(request);
    }

    @Override
    public void onPush_UpdateBasicQuote(FTAPI_Conn client, Response response) {
        if (response.getRetType() != ErrorCodeEnum.SUCCESS.getCode()) {
            log.error("股票数据更新失败: {}", response.getRetMsg());
            return;
        }

        try {
            //TODO： 做一层对象转换
            String json = JSON.toJSONString(response);
            // 发送更新消息给前端
            eventPublisher.publishEvent(new StockQuoteEvent(json));
            // stockWebSocketHandler.broadcastStockPrice(json);
            log.info("收到股票数据更新: {}", json);
        } catch (Exception e) {
            log.error("处理股票数据失败, response = {}",JSON.toJSONString(response), e);
        }
    }
}
