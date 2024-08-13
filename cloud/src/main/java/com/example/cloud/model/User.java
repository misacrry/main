package com.example.cloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;

    private String addr;

    private Integer age;

    private String name;

    private String password;

    private String wxOpenId;

    private String wxNickName;

    private Integer roleId;
}
