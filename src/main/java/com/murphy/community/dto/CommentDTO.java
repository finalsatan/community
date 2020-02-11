package com.murphy.community.dto;

import com.murphy.community.model.User;
import lombok.Data;

/**
 * CommentDTO
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/10 5:18 下午
 */

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
    private Integer commentCount;
}
