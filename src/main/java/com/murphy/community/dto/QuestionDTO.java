package com.murphy.community.dto;

import com.murphy.community.model.User;
import lombok.Data;

/**
 * QuestionDTO
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/4 4:47 下午
 */

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
