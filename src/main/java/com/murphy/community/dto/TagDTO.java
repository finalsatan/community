package com.murphy.community.dto;

import lombok.Data;

import java.util.List;

/**
 * TagDTO
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/11 11:04 下午
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
