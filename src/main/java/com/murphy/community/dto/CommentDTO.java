package com.murphy.community.dto;

import lombok.Data;

/**
 * CommentDTO
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/9 9:05 下午
 */

@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
