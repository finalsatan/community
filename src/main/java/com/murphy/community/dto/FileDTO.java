package com.murphy.community.dto;

import lombok.Data;

/**
 * FileDTO
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/12 10:31 下午
 */

@Data
public class FileDTO {
    /**
     * 0 表示上传失败，1 表示上传成功
     */
    private int success;
    /**
     * 提示的信息，上传成功或上传失败及错误信息等。
     */
    private String message;
    /**
     * 图片地址,上传成功时才返回
     */
    private String url;
}
