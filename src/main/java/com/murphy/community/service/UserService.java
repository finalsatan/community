package com.murphy.community.service;

import com.murphy.community.mapper.UserMapper;
import com.murphy.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserService
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/6 12:35 上午
 */

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if (dbUser==null){
            user.setCreatedAt(System.currentTimeMillis());
            user.setUpdatedAt(user.getCreatedAt());
            userMapper.insert(user);
        }else{
            dbUser.setUpdatedAt(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            userMapper.update(dbUser);
        }
    }
}
