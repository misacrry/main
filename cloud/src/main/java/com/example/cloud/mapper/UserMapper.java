package com.example.cloud.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    Integer checkUser(String phone, String password);

}
