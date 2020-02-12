package com.murphy.community.dto;

import com.murphy.community.model.User;
import lombok.Data;

/**
 * NotificationDTO
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/12 2:45 下午
 */

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private Long outerId;
    private String outerTitle;
    private String typeName;
    private Integer type;
}
