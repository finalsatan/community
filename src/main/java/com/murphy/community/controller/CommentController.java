package com.murphy.community.controller;

import com.murphy.community.dto.CommentCreateDTO;
import com.murphy.community.dto.CommentDTO;
import com.murphy.community.dto.ResultDTO;
import com.murphy.community.enums.CommentTypeEnum;
import com.murphy.community.exception.CustomizeErrorCode;
import com.murphy.community.model.Comment;
import com.murphy.community.model.User;
import com.murphy.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResultDTO post(@RequestBody CommentCreateDTO commentCreateDTO,
                          HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }

        if (commentCreateDTO == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        commentCreateDTO.setContent(commentCreateDTO.getContent().trim());
        if (commentCreateDTO.getContent().isEmpty()) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }


    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO<List<CommentDTO>> comments(@PathVariable Long id) {
        List<CommentDTO> commentDTOs = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOs);
    }
}
