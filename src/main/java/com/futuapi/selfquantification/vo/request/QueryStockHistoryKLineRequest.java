package com.futuapi.selfquantification.vo.request;

import lombok.Data;

/**
 * @author 周欣
 * @date 2025/4/2 17:56
 */
@Data
public class QueryStockHistoryKLineRequest {


    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 时间类型,日线，周线，月线，季度线，年线
     */
    private Integer timeType;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

}
