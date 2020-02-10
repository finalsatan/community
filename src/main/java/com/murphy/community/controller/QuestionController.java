package com.murphy.community.controller;

import com.murphy.community.dto.CommentCreateDTO;
import com.murphy.community.dto.CommentDTO;
import com.murphy.community.dto.QuestionDTO;
import com.murphy.community.service.CommentService;
import com.murphy.community.service.QuestionService;
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

    @GetMapping("/question/{id}")
    public String question(@PathVariable Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> comments = commentService.listByQuestionId(id);

        //累加阅读数
        questionService.incView(id);

        model.addAttribute("question", questionDTO);
        model.addAttribute("comments",comments);

        return "question";
    }
}
