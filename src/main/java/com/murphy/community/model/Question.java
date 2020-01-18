package com.murphy.community.model;

import lombok.Data;

/**
 * Question
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/1/18 7:31 下午
 */

@Data
public class Question {
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
}
