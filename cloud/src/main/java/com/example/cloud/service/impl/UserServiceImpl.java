package com.example.cloud.service.impl;

import com.example.cloud.mapper.UserMapper;
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
}
