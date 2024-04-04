package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Mapper
public interface UserMapper {


    @Select("select * from user")
    public List<User> getUser();

    @Insert("insert into user(username,password) values(#{username},#{password})")
    public int addUser(User user);

    @Select("select * from user where username=#{username} and password=#{password}")
    User login(User user);
}
