package com.example.cloud.controller;

import com.example.cloud.model.FireControl;
import com.example.cloud.service.FireService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/fireControl")
public class FireController {

    @Resource
    public FireService fireService;

    @GetMapping("/faultCounts")
    public FireControl faultCounts() {
        return fireService.faultCounts();
    }

}
