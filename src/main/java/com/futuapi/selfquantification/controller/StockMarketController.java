package com.futuapi.selfquantification.controller;

import com.alibaba.fastjson2.JSON;
import com.futuapi.selfquantification.enums.ErrorCodeEnum;
import com.futuapi.selfquantification.vo.ApiResponse;
import com.futuapi.selfquantification.vo.StockInfoDetailVO;
import com.futuapi.selfquantification.vo.request.QueryStockQuoteRequest;
import io.micrometer.common.util.StringUtils;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 周欣
 * @date 2025/3/31 15:39
 */
@RestController
@RequestMapping("/api/stock/stockMarket")
@Slf4j
public class StockMarketController {

    @RequestMapping(value = "/stock/quote", method = RequestMethod.GET)
    public ApiResponse<StockInfoDetailVO> QueryStockQuote(@RequestParam QueryStockQuoteRequest request) {
        if (Objects.isNull(request)) {
            return ApiResponse.error(ErrorCodeEnum.PARAM_ERROR);
        }
        if (StringUtils.isBlank(request.getStockCode()) || Objects.isNull(request.getMarketId())) {
            return ApiResponse.error(ErrorCodeEnum.PARAM_ERROR);
        }
        try {

        } catch (Exception e) {
            log.error("StockMarketController QueryStockQuote error, request = {},", JSON.toJSONString(request), e);
        }
        return ApiResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
    }

}
