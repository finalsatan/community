package com.murphy.community.mapper;

import com.murphy.community.model.User;
import org.apache.ibatis.annotations.*;
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

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(String accountId);

    @Update("update user set name=#{name},token=#{token},avatar_url=#{avatarUrl},updated_at=#{updatedAt} where id=#{id}")
    void update(User dbUser);
}


