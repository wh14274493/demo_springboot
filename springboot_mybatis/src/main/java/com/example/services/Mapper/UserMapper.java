package com.example.services.Mapper;

import com.example.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "user_name",property = "userName"),
            @Result(column = "password",property = "password"),
            @Result(column = "name",property = "name")
    })
    List<User> findAll();

    @Select("select * from user where id=#{id}")
    List<User> findById(Long id);


    @Delete("delete from user where id = #{id}")
    Boolean deleteById(Long id);

    @Insert("insert into user(id,user_name,password,name)values(#{id},#{userName},#{password},#{name})")
    Boolean insert(User u);
}
