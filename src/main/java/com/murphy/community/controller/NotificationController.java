package com.murphy.community.controller;

import com.murphy.community.dto.CommentDTO;
import com.murphy.community.dto.NotificationDTO;
import com.murphy.community.dto.ResultDTO;
import com.murphy.community.enums.CommentTypeEnum;
import com.murphy.community.enums.NotificationTypeEnum;
import com.murphy.community.model.User;
import com.murphy.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * NotificationController
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/12 4:25 下午
 */

@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notification(HttpServletRequest request, @PathVariable Long id) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (notificationDTO.getType() == NotificationTypeEnum.REPLY_COMMENT.getType() ||
                notificationDTO.getType() == NotificationTypeEnum.REPLY_QUESTION.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        }

        return "redirect:/";
    }

}
