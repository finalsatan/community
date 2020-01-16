package com.murphy.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * PublishController
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/1/16 10:51 下午
 */
@Controller
public class PublishController {
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
}
