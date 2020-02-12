package com.murphy.community.enums;

import lombok.AllArgsConstructor;

/**
 * NotificationTypeEnum
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/12 2:02 下午
 */

@AllArgsConstructor
public enum NotificationTypeEnum {
    /**
     * REPLY_QUESTION
     */
    REPLY_QUESTION(1,"回复了问题"),
    /**
     * REPLY_COMMENT
     */
    REPLY_COMMENT(2,"回复了评论"),
    ;
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String nameOfType(int type){
        for (NotificationTypeEnum typeEnum : NotificationTypeEnum.values()) {
            if (type == typeEnum.getType()){
                return typeEnum.getName();
            }
        }

        return "";
    }
}
