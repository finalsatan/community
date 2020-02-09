package com.murphy.community.dto;

import com.murphy.community.exception.CustomizeErrorCode;
import com.murphy.community.exception.CustomizeException;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ResultDTO
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/9 9:39 下午
 */

@Data
@AllArgsConstructor
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code, String message) {
        return new ResultDTO(code, message);
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return new ResultDTO(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO okOf(){
        return new ResultDTO(200,"成功");
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return new ResultDTO(e.getCode(),e.getMessage());
    }
}
