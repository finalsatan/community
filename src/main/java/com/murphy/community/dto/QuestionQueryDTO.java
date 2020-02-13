package com.murphy.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * QuestionQueryDTO
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/13 2:47 下午
 */

@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
