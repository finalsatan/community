package com.murphy.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.murphy.community.dto.QuestionDTO;
import com.murphy.community.dto.QuestionQueryDTO;
import com.murphy.community.exception.CustomizeErrorCode;
import com.murphy.community.exception.CustomizeException;
import com.murphy.community.exception.ICustomizeErrorCode;
import com.murphy.community.mapper.QuestionExtMapper;
import com.murphy.community.mapper.QuestionMapper;
import com.murphy.community.model.Question;
import com.murphy.community.model.QuestionExample;
import com.murphy.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserService userService;

    public PageInfo<QuestionDTO> list(String search, Integer page, Integer size) {
//        example.setOrderByClause("`gmt_modified` DESC, `id` DESC");

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setPage(page);
        questionQueryDTO.setSize(size);
        search = StringUtils.trim(search);
        if (!StringUtils.isBlank(search)) {
            String[] keyWords = StringUtils.split(search, ' ');
            String regexpKeyWords = Arrays.stream(keyWords).collect(Collectors.joining("|"));
            questionQueryDTO.setSearch(regexpKeyWords);
        }else{
            questionQueryDTO.setSearch(null);
        }

        PageHelper.startPage(page, size);
        PageInfo<Question> questionPageInfo = new PageInfo<>(questionExtMapper.search(questionQueryDTO));

        PageInfo<QuestionDTO> questionDTOsPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questionPageInfo, questionDTOsPageInfo);

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionPageInfo.getList()) {
            User user = userService.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        questionDTOsPageInfo.setList(questionDTOList);

        return questionDTOsPageInfo;
    }

    public PageInfo<QuestionDTO> list(Long userId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("`gmt_modified` DESC, `id` DESC");
        example.createCriteria().andCreatorEqualTo(userId);
        PageInfo<Question> questionPageInfo = new PageInfo<>(questionMapper.selectByExample(example));

        PageInfo<QuestionDTO> questionDTOsPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questionPageInfo, questionDTOsPageInfo);

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionPageInfo.getList()) {
            User user = userService.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        questionDTOsPageInfo.setList(questionDTOList);

        return questionDTOsPageInfo;
    }

    public QuestionDTO getById(Long id) {
        QuestionDTO questionDTO = new QuestionDTO();

        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        BeanUtils.copyProperties(question, questionDTO);

        User user = userService.findById(question.getCreator());

        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        } else {
            Question updateQuestion = new Question();
            updateQuestion.setId(question.getId());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setGmtModified(System.currentTimeMillis());

            int updated = questionMapper.updateByPrimaryKeySelective(updateQuestion);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incViewCount(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }

        Question question = new Question();
        question.setId(queryDTO.getId());
        String[] tags = StringUtils.split(queryDTO.getTag(), ',');
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));

        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOs = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());

        return questionDTOs;
    }
}
