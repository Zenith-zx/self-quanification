package com.futuapi.selfquantification.vo.request;

import lombok.Data;

/**
 * @author 周欣
 * @date 2025/3/31 16:52
 */
@Data
public class QueryStockQuoteRequest {

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 市场ID
     * 1-港股市场
     * 11-美股市场
     * 21-上海市场
     * 22-深圳市场
     */
    private Integer marketId;

}
