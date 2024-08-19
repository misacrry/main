package com.example.cloud.service.impl;

import com.example.cloud.mapper.UserMapper;
import com.example.cloud.model.User;
import com.example.cloud.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public Integer checkUser(String phone, String password) {
        return userMapper.checkUser(phone, password);
    }

    @Override
    public boolean addOne(User user) {
        return userMapper.addOne(user);
    }

    @Override
    public User selectOneByPhone(String phone) {
        return userMapper.selectOneByPhone(phone);
    }
}
