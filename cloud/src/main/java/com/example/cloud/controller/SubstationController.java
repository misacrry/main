package com.example.cloud.controller;

import com.example.cloud.model.Substation;
import com.example.cloud.service.SubstationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/substation")
public class SubstationController {
    @Resource
    private SubstationService substationService;

    @GetMapping("/runningStatus")
    public Substation runningStatus(){
        return substationService.runningStatus();
    }
}
