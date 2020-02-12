package com.murphy.community.service;

import com.murphy.community.mapper.UserMapper;
import com.murphy.community.model.User;
import com.murphy.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);
        if (dbUsers.size() == 0) {
            user.setCreatedAt(System.currentTimeMillis());
            user.setUpdatedAt(user.getCreatedAt());
            userMapper.insert(user);
        } else {
            User dbUser = dbUsers.get(0);
            User updateUser = new User();
            updateUser.setUpdatedAt(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setToken(user.getToken());
            updateUser.setName(user.getName());
            updateUser.setId(dbUser.getId());
            userMapper.updateByPrimaryKeySelective(updateUser);
        }
    }

    public User findByToken(String token) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() != 0) {
            return users.get(0);
        }
        return null;
    }

    public User findById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public Map<Long,User> findByIds(List<Long> ids){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(ids);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        return userMap;
    }
}
