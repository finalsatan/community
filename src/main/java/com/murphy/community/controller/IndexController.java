package com.murphy.community.controller;

import com.github.pagehelper.PageInfo;
import com.murphy.community.dto.QuestionDTO;
import com.murphy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * IndexController
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/5 3:16 下午
 */

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "7") Integer size,
                        @RequestParam(name = "search", required = false) String search) {

        PageInfo<QuestionDTO> questionPageInfo = questionService.list(search, page, size);
        model.addAttribute("questions", questionPageInfo);
        model.addAttribute("search", search);
        return "index";
    }
}
