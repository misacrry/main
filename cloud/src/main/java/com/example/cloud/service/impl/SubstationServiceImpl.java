package com.example.cloud.service.impl;

import com.example.cloud.mapper.SubstationMapper;
import com.example.cloud.model.Substation;
import com.example.cloud.service.SubstationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SubstationServiceImpl implements SubstationService {

    @Resource
    SubstationMapper substationMapper;

    @Override
    public Substation runningStatus() {
        return substationMapper.runningStatus();
    }
}
