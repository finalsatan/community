package com.murphy.community.mapper;

import com.murphy.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author 233murphy
 */
@Mapper
@Repository
public interface UserMapper {


    @Insert("insert into user (name,account_id,token,created_at,updated_at,avatar_url) values (#{name},#{accountId},#{token},#{createdAt},#{updatedAt},#{avatarUrl})")
    void insert(User user);


    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
}


