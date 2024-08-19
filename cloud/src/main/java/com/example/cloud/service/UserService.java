package com.example.cloud.service;

import com.example.cloud.model.User;

public interface UserService {

    User selectOneByPhone(String phone);

    boolean addOne(User user);

    Integer checkUser(String phone, String password);


}
