package com.example.cloud.service;

import com.example.cloud.model.GridConnectionPoint;

import java.util.List;

public interface GridConnectionPointService {

    List<GridConnectionPoint> selectList();

    List<GridConnectionPoint> selectListBySubId(Integer subId);

    int deleteBySubId(Integer subId);

    int deleteById(Integer id);

    int addOne(GridConnectionPoint gridConnectionPoint);
}
