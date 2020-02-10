package com.murphy.community.dto;

import lombok.Data;

/**
 * CommentCreateDTO
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/9 9:05 下午
 */

@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
