package com.murphy.community.exception;

/**
 * CustomizeException
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/7 8:49 下午
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode iCustomizeErrorCode) {
        this.code = iCustomizeErrorCode.getCode();
        this.message = iCustomizeErrorCode.getMessage();
    }

    public CustomizeException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
