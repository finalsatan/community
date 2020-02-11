package com.murphy.community.controller;

import com.murphy.community.dto.CommentDTO;
import com.murphy.community.dto.QuestionDTO;
import com.murphy.community.enums.CommentTypeEnum;
import com.murphy.community.mapper.QuestionExtMapper;
import com.murphy.community.model.Question;
import com.murphy.community.service.CommentService;
import com.murphy.community.service.QuestionService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * QuestionController
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/5 11:38 下午
 */

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @GetMapping("/question/{id}")
    public String question(@PathVariable Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累加阅读数
        questionService.incView(id);

        model.addAttribute("question", questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);

        return "question";
    }
}
