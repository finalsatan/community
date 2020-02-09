package com.murphy.community.enums;

import lombok.AllArgsConstructor;

/**
 * CommentTypeEnum
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/9 9:50 下午
 */

@AllArgsConstructor
public enum CommentTypeEnum {
    /**
     * QUESTION
     */
    QUESTION(1),
    /**
     * COMMENT
     */
    COMMENT(2);

    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
