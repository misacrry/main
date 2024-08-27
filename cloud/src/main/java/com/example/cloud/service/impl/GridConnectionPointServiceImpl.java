package com.example.cloud.service.impl;

import com.example.cloud.mapper.GridConnectionPointMapper;
import com.example.cloud.model.GridConnectionPoint;
import com.example.cloud.service.GridConnectionPointService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GridConnectionPointServiceImpl implements GridConnectionPointService {

    @Resource
    GridConnectionPointMapper gridConnectionPointMapper;

    @Override
    public List<GridConnectionPoint> selectList() {
        return gridConnectionPointMapper.selectList();
    }

    @Override
    public List<GridConnectionPoint> selectListBySubId(Integer subId) {
        return gridConnectionPointMapper.selectListBySubId(subId);
    }

    @Override
    public int deleteById(Integer id) {
        return gridConnectionPointMapper.deleteById(id);
    }

    @Override
    public int deleteBySubId(Integer subId) {
        return gridConnectionPointMapper.deleteBySubId(subId);
    }

    @Override
    public int addOne(GridConnectionPoint gridConnectionPoint) {
        return gridConnectionPointMapper.addOne(gridConnectionPoint);
    }
}
