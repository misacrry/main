package com.example.cloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private String addr;

    private Integer age;

    private String name;

    private String password;

    private String phone;

    private String wxOpenId;

    private String wxNickName;

    private Integer roleId;
}
