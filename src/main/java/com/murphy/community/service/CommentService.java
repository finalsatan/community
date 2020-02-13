package com.murphy.community.service;

import com.murphy.community.dto.CommentDTO;
import com.murphy.community.enums.CommentTypeEnum;
import com.murphy.community.enums.NotificationStatusEnum;
import com.murphy.community.enums.NotificationTypeEnum;
import com.murphy.community.exception.CustomizeErrorCode;
import com.murphy.community.exception.CustomizeException;
import com.murphy.community.mapper.*;
import com.murphy.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    CommentExtMapper commentExtMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    NotificationMapper notificationMapper;

    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.QUESTION.getType()) {
            //回复问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            dbQuestion.setCommentCount(1);
            questionExtMapper.incCommentCount(dbQuestion);

            //创建通知
            createNotification(comment, dbQuestion.getCreator(), commentator.getName(), dbQuestion.getTitle(), NotificationTypeEnum.REPLY_QUESTION, dbQuestion.getId());

        } else if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            //获取问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (dbQuestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);

            // 增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);

            //创建通知
            createNotification(comment, dbComment.getCommentator(), commentator.getName(), dbQuestion.getTitle(), NotificationTypeEnum.REPLY_COMMENT, dbQuestion.getId());
        }
    }

    private void createNotification(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum type, Long outerId) {
        if (receiver == comment.getCommentator()){
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setType(type.getType());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());

        List<Comment> comments = commentMapper.selectByExample(example);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人信息
        Map<Long, User> userMap = userService.findByIds(userIds);


        //转换成 DTO
        List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(commentDTO.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOs;
    }
}
