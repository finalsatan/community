package com.murphy.community.exception;

/**
 * CustomizeException
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/7 8:49 下午
 */
public class CustomizeException extends RuntimeException{
    private String message;
    public CustomizeException(ICustomizeErrorCode iCustomizeErrorCode){
        this.message = iCustomizeErrorCode.getMessage();
    }

    public CustomizeException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
