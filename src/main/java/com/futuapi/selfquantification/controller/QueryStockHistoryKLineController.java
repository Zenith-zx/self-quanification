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
    public ApiResponse<String> queryStockHistoryKLine(QueryStockHistoryKLineRequest request) {
        // 参数校验
        if (Objects.isNull(request) || StringUtils.isBlank(request.getStockCode()) || Objects.isNull(request.getTimeType())) {
            return ApiResponse.error(ErrorCodeEnum.PARAM_ERROR);
        }

        // 设置接口超时时间为400ms
        DeferredResult<String> result = new DeferredResult<>(400L);

        String requestKey = HISTORY_K_LINE_PREFIX + request.getStockCode() + request.getTimeType();

        queryStockHistoryKLineService.queryStockHistoryKLine(request);

        // 设置超时回调
        result.onTimeout(() -> {
            queryStockHistoryKLineService.removePendingRequest(requestKey);
            result.setErrorResult(ResponseEntity.status(ErrorCodeEnum.TIME_OUT.getCode()).body("Timeout"));
        });

        String resultResult = (String) result.getResult();
        return ApiResponse.success(resultResult);
    }

}
