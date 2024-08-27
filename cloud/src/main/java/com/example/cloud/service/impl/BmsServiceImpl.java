package com.example.cloud.service.impl;

import com.example.cloud.mapper.BmsMapper;
import com.example.cloud.model.Bms;
import com.example.cloud.service.BmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class BmsServiceImpl implements BmsService {

    @Resource
    private BmsMapper bmsMapper;

    @Override
    public List<Bms> selectBmsListByPointId(Integer pointId) {
        return bmsMapper.selectBmsListByPointId(pointId);
    }

    @Override
    public Bms selectFaultCounts() {
        return bmsMapper.selectFaultCounts();
    }

    @Override
    public int deleteById(Integer id) {
        return bmsMapper.deleteById(id);
    }

    @Override
    public int deleteByPointId(Integer pointId) {
        return bmsMapper.deleteByPointId(pointId);
    }

    @Override
    public int addOne(Bms bms) {
        return bmsMapper.addOne(bms);
    }

}
