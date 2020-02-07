package com.murphy.community.exception;

/**
 * CustomizeErrorCode
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/7 8:59 下午
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("你找的问题不存在，换个试试吧！");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
