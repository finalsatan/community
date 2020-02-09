package com.murphy.community.service;

import com.murphy.community.enums.CommentTypeEnum;
import com.murphy.community.exception.CustomizeErrorCode;
import com.murphy.community.exception.CustomizeException;
import com.murphy.community.mapper.CommentMapper;
import com.murphy.community.mapper.QuestionExtMapper;
import com.murphy.community.mapper.QuestionMapper;
import com.murphy.community.model.Comment;
import com.murphy.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CommentService
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/9 9:53 下午
 */

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.QUESTION.getType()){
            //回复问题
           Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            dbQuestion.setCommentCount(1);
            questionExtMapper.incCommentCount(dbQuestion);
        }else if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment  = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            commentMapper.insert(comment);
        }
    }
}
