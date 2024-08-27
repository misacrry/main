package com.example.cloud.controller;

import com.example.cloud.common.ApiResult;
import com.example.cloud.service.BackTreeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BackTreeController {

    @Resource
    private BackTreeService backTreeService;


    @GetMapping("/getBackTree")
    public ApiResult getTree(){
        System.out.println(backTreeService.getTree());
        return backTreeService.getTree();
    }

}
