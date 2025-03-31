package com.futuapi.selfquantification.vo;

import com.futuapi.selfquantification.enums.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 周欣
 * @date 2025/3/31 16:26
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private int code;

    private String message;

    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getDesc(), data);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getDesc(), null);
    }

    public static <T> ApiResponse<T> error(ErrorCodeEnum errorCodeEnum) {
        return new ApiResponse<>(errorCodeEnum.getCode(), errorCodeEnum.getDesc(), null);
    }

    public static <T> ApiResponse<T> success(ErrorCodeEnum errorCodeEnum, T data) {
        return new ApiResponse<>(errorCodeEnum.getCode(), errorCodeEnum.getDesc(), data);
    }

    public boolean isSuccess() {
        return code == 0;
    }
}
