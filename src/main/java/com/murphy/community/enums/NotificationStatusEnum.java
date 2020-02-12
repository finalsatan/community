package com.murphy.community.enums;

import lombok.AllArgsConstructor;

/**
 * NotificationStatusEnum
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/12 2:02 下午
 */

@AllArgsConstructor
public enum NotificationStatusEnum {
    /**
     * UNREAD
     */
    UNREAD(0),
    /**
     * READ
     */
    READ(1),
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
