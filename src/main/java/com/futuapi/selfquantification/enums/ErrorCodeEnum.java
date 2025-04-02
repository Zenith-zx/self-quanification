package com.futuapi.selfquantification.enums;

import lombok.Getter;

/**
 * @author 周欣
 * @date 2025/3/31 16:27
 */
@Getter
public enum ErrorCodeEnum {

    SUCCESS(0, "success"),
    PARAM_ERROR(1, "param error"),

    TIME_OUT(2, "time out"),
    SYSTEM_ERROR(500, "system error")
    ;


    ErrorCodeEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }


    private final  int code;


    private final  String desc;
}
