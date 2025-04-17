package com.futuapi.selfquantification.controller;

import com.futuapi.selfquantification.enums.ErrorCodeEnum;
import com.futuapi.selfquantification.service.QueryStockHistoryKLineService;
import com.futuapi.selfquantification.vo.ApiResponse;
import com.futuapi.selfquantification.vo.request.QueryStockHistoryKLineRequest;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author 周欣
 * @date 2025/4/2 20:04
 */
@Slf4j
@RestController
@RequestMapping(("/api/stock"))
public class QueryStockHistoryKLineController {

    @Resource
    private QueryStockHistoryKLineService queryStockHistoryKLineService;

    public final static String HISTORY_K_LINE_PREFIX = "historyKLine";

    @RequestMapping(value = "/history/kline", method = RequestMethod.POST)
    @ResponseBody
    public DeferredResult<String> queryStockHistoryKLine(QueryStockHistoryKLineRequest request) {
        // 参数校验
        if (Objects.isNull(request) || StringUtils.isBlank(request.getStockCode()) || Objects.isNull(request.getTimeType())) {
            DeferredResult<String> errorResult = new DeferredResult<>();
            errorResult.setResult(ErrorCodeEnum.PARAM_ERROR.getDesc());
            return errorResult;
        }

        // 设置接口超时时间为400ms
        DeferredResult<String> result = new DeferredResult<>(400L);

        String requestKey = String.format("%s_%s_%s", QueryStockHistoryKLineController.HISTORY_K_LINE_PREFIX, request.getStockCode(), "1");

        // 将 DeferredResult 存入缓存
        queryStockHistoryKLineService.addPendingRequest(requestKey, result);


        queryStockHistoryKLineService.queryStockHistoryKLine(request);

        // 设置超时回调
        result.onTimeout(() -> {
            queryStockHistoryKLineService.removePendingRequest(requestKey);
            result.setErrorResult(ResponseEntity.status(ErrorCodeEnum.TIME_OUT.getCode()).body("Timeout"));
        });
        return result;
    }

}
