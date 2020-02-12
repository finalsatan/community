package com.murphy.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.murphy.community.dto.NotificationDTO;
import com.murphy.community.enums.NotificationStatusEnum;
import com.murphy.community.enums.NotificationTypeEnum;
import com.murphy.community.exception.CustomizeErrorCode;
import com.murphy.community.exception.CustomizeException;
import com.murphy.community.mapper.NotificationMapper;
import com.murphy.community.mapper.UserMapper;
import com.murphy.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * NotificationService
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/12 2:43 下午
 */

@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    public PageInfo<NotificationDTO> list(Long userId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        NotificationExample example = new NotificationExample();
        example.setOrderByClause("`gmt_create` DESC, `id` DESC");
        example.createCriteria().andReceiverEqualTo(userId);
        PageInfo<Notification> notificationPageInfo = new PageInfo<>(notificationMapper.selectByExample(example));

        PageInfo<NotificationDTO> notificationDTOsPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(notificationPageInfo, notificationDTOsPageInfo);

        List<NotificationDTO> notificationDTOList = new ArrayList<>();

        for (Notification notification : notificationPageInfo.getList()) {
            NotificationDTO notificationDTO = new NotificationDTO();

            notificationDTO.setId(notification.getId());
            notificationDTO.setGmtCreate(notification.getGmtCreate());
            notificationDTO.setStatus(notification.getStatus());
            notificationDTO.setNotifier(notification.getNotifier());
            notificationDTO.setNotifierName(notification.getNotifierName());
            notificationDTO.setOuterId(notification.getOuterId());
            notificationDTO.setOuterTitle(notification.getOuterTitle());
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

            notificationDTOList.add(notificationDTO);
        }

        notificationDTOsPageInfo.setList(notificationDTOList);

        return notificationDTOsPageInfo;
    }

    public Long unreadCount(Long userId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        Long unreadCount = notificationMapper.countByExample(example);
        return unreadCount;
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);

        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }

        if (notification.getReceiver() != user.getId()) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        NotificationDTO notificationDTO = new NotificationDTO();

        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

        Notification update = new Notification();
        update.setId(notification.getId());
        update.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKeySelective(update);

        return notificationDTO;
    }
}
