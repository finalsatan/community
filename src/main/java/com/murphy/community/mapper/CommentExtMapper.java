package com.murphy.community.mapper;

import com.murphy.community.model.Comment;

/**
 * CommentExtMapper
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/11 5:49 下午
 */
public interface CommentExtMapper {
    int incCommentCount(Comment record);
}
