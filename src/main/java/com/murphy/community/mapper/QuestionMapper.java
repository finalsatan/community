package com.murphy.community.mapper;

import com.github.pagehelper.Page;
import com.murphy.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * QuestionMapper
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/1/18 7:27 下午
 */
@Mapper
@Repository
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question order by gmt_modified desc, id desc")
    Page<Question> list();

    @Select("select * from question where creator=#{userId} order by gmt_modified desc, id desc")
    Page<Question> listByUserId(Integer userId);
}
