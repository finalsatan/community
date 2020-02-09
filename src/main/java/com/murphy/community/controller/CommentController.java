package com.murphy.community.controller;

import com.murphy.community.dto.CommentDTO;
import com.murphy.community.dto.ResultDTO;
import com.murphy.community.exception.CustomizeErrorCode;
import com.murphy.community.model.Comment;
import com.murphy.community.model.User;
import com.murphy.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * CommentController
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/9 9:01 下午
 */

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public ResultDTO post(@RequestBody CommentDTO commentDTO,
                          HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
