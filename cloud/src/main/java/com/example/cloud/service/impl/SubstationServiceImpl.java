package com.example.cloud.service.impl;

import com.example.cloud.mapper.SubstationMapper;
import com.example.cloud.model.Substation;
import com.example.cloud.service.SubstationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SubstationServiceImpl implements SubstationService {

    @Resource
    SubstationMapper substationMapper;

    @Override
    public int addSub(Substation substation) {
        return substationMapper.addSub(substation);
    }

    @Override
    public int delSubById(Integer id) {
        return substationMapper.delSubById(id);
    }

    @Override
    public List<Substation> selectSubList() {
        return substationMapper.selectSubList();
    }
}
