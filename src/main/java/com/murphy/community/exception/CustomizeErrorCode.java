package com.murphy.community.exception;

/**
 * CustomizeErrorCode
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/7 8:59 下午
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    /**
     * QUESTION_NOT_FOUND
     */
    QUESTION_NOT_FOUND(2001, "你找的问题不存在，换个试试吧！"),
    /**
     * TARGET_PARAM_NOT_FOUND
     */
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    /**
     * NOT_LOGIN
     */
    NOT_LOGIN(2003,"当前操作需要登录"),
    /**
     * SYS_ERROR
     */
    SYS_ERROR(2004,"哦，出 bug 了，稍后再来吧！！！"),
    /**
     * TYPE_PARAM_WRONG
     */
    TYPE_PARAM_WRONG(2005,"评论类型不存在"),
    /**
     * COMMENT_NOT_FOUND
     */
    COMMENT_NOT_FOUND(2006,"评论不存在"),
    /**
     * CONTENT_IS_EMPTY
     */
    CONTENT_IS_EMPTY(2007,"输入内容不能为空"),
    /**
     * READ_NOTIFICATION_FAIL
     */
    READ_NOTIFICATION_FAIL(2008,"这不是您的信息"),
    /**
     * NOTIFICATION_NOT_FOUND
     */
    NOTIFICATION_NOT_FOUND(2009, "你找的通知不存在，换个试试吧！"),
    /**
     * INVALID_INPUT
     */
    INVALID_INPUT(2401,"参数不正确")
    ;

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
