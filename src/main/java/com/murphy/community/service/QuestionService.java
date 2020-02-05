package com.murphy.community.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.murphy.community.dto.QuestionDTO;
import com.murphy.community.mapper.QuestionMapper;
import com.murphy.community.mapper.UserMapper;
import com.murphy.community.model.Question;
import com.murphy.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * QuestionService
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/4 4:51 下午
 */

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PageInfo<QuestionDTO> list(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        PageInfo<Question> questionPageInfo = new PageInfo<>(questionMapper.list());

        PageInfo<QuestionDTO> questionDTOsPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questionPageInfo, questionDTOsPageInfo);

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionPageInfo.getList()) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        questionDTOsPageInfo.setList(questionDTOList);

        return questionDTOsPageInfo;
    }

    public PageInfo<QuestionDTO> list(Integer userId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        PageInfo<Question> questionPageInfo = new PageInfo<>(questionMapper.listByUserId(userId));

        PageInfo<QuestionDTO> questionDTOsPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questionPageInfo, questionDTOsPageInfo);

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionPageInfo.getList()) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        questionDTOsPageInfo.setList(questionDTOList);

        return questionDTOsPageInfo;
    }

    public QuestionDTO getById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();

        Question question = questionMapper.findById(id);
        BeanUtils.copyProperties(question,questionDTO);

        User user = userMapper.findById(question.getCreator());

        questionDTO.setUser(user);
        return questionDTO;
    }
}
