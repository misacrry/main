package com.example.cloud.mapper;

import com.example.cloud.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectOneByPhone(String phone);

    boolean addOne(User user);

    Integer checkUser(String phone, String password);

}
