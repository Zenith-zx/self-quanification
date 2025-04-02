package com.futuapi.selfquantification.enums;

import lombok.Getter;

/**
 * @author 周欣
 * @date 2025/4/2 20:07
 */
@Getter
public enum TimeTypeEnum {

    ONE_MINUTE(1, "1分钟"),
    FIVE_MINUTE(2, "5分钟"),
    TEN_MINUTE(3, "10分钟"),
    FIFTEEN_MINUTE(4, "15分钟"),
    THIRTY_MINUTE(5, "30分钟"),
    ONE_HOUR(6, "小时"),
    ONE_DAY(7, "日"),
    ONE_WEEK(8, "周"),
    ONE_MONTH(9, "月"),
    ONE_QUARTER(10, "季度"),
    ONE_YEAR(11, "年")



    ;

    TimeTypeEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    private final int code;

    private final String desc;
}
