package com.futuapi.selfquantification.vo;

import lombok.Data;

/**
 * @author 周欣
 * @date 2025/3/31 16:48
 */
@Data
public class StockInfoDetailVO {

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 当前价
     */
    private String currentPrice;

    /**
     * 开盘价
     */
    private String openPrice;

    /**
     * 收盘价
     */
    private String closePrice;

    /**
     * 当日最高价
     */
    private String highestPrice;

    /**
     * 当日最低价
     */
    private String lowestPrice;

    /**
     * 成交量
     */
    private Long volume;

    /**
     * 更新时间
     */
    private String updateTime;

}
