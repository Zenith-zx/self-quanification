package com.futuapi.selfquantification.service;

import com.alibaba.fastjson2.JSON;
import com.futu.openapi.FTAPI_Conn;
import com.futu.openapi.FTAPI_Conn_Qot;
import com.futu.openapi.FTSPI_Conn;
import com.futu.openapi.FTSPI_Qot;
import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotRequestHistoryKL;
import com.futuapi.selfquantification.controller.QueryStockHistoryKLineController;
import com.futuapi.selfquantification.enums.ErrorCodeEnum;
import com.futuapi.selfquantification.utils.TimeUtils;
import com.futuapi.selfquantification.vo.request.QueryStockHistoryKLineRequest;
import com.google.common.collect.Maps;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author 周欣
 * @date 2025/4/2 14:35
 */
@Service
@Slf4j
public class QueryStockHistoryKLineService implements FTSPI_Qot, FTSPI_Conn {

    private final Map<String, DeferredResult<String>> pendingRequests = Maps.newConcurrentMap();

    @Resource
    private FTAPI_Conn_Qot qot;

    @PostConstruct
    public void init() {
        qot.setConnSpi(this);
        qot.setQotSpi(this);
    }

    // 添加请求到缓存
    public void addPendingRequest(String key, DeferredResult<String> deferredResult) {
        pendingRequests.put(key, deferredResult);
    }

    // 移除请求
    public void removePendingRequest(String key) {
        pendingRequests.remove(key);
    }



    @Override
    public void onReply_RequestHistoryKL(FTAPI_Conn client, int nSerialNo, QotRequestHistoryKL.Response rsp) {
        if (rsp.getRetType() != ErrorCodeEnum.SUCCESS.getCode()) {
            System.out.printf("QotRequestHistoryKL failed: %s\n", rsp.getRetMsg());
        }
        else {
            try {
                String json = JSON.toJSONString(rsp);
                log.info("Receive QotRequestHistoryKL: {}", json);
                String requestKey = String.format("%s_%s_%s", QueryStockHistoryKLineController.HISTORY_K_LINE_PREFIX, "00700", "1");
                DeferredResult<String> deferredResult = pendingRequests.get(requestKey);
                if (Objects.nonNull(deferredResult)) {
                    deferredResult.setResult(json);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void queryStockHistoryKLine(QueryStockHistoryKLineRequest request) {
        QotCommon.Security sec = QotCommon.Security.newBuilder()
            .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
            .setCode(request.getStockCode())
            .build();
        QotRequestHistoryKL.C2S c2s = QotRequestHistoryKL.C2S.newBuilder()
            .setRehabType(QotCommon.RehabType.RehabType_Forward_VALUE)
            .setKlType(QotCommon.KLType.KLType_Day_VALUE)
            .setSecurity(sec)
            .setBeginTime(TimeUtils.secondTimestampToDateString(request.getStartTime()))
            .setEndTime(TimeUtils.secondTimestampToDateString(request.getEndTime()))
            .build();
        QotRequestHistoryKL.Request req = QotRequestHistoryKL.Request.newBuilder().setC2S(c2s).build();
        int seqNo = qot.requestHistoryKL(req);
    }
}
