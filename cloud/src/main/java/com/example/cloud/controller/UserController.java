package com.example.cloud.controller;

import com.example.cloud.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/loginCheck")
    public Integer loginCheck(String phone, String password){
        System.out.println(userService.checkUser("16060640680", "Test"));
        return userService.checkUser("16060640680", "Test");
    }

    public static void main(String[] args) {
        UserController userController = new UserController();
        Integer id = userController.loginCheck("16060640680", "Test");
        System.out.println(id);
    }
}
