package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IUserService implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> getUser() {
        return userMapper.getUser();
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    public User login(User user) {
        return userMapper.login(user);
    }
}
