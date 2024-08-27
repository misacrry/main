package com.example.cloud.service;

import com.example.cloud.model.Bms;

import java.util.List;

public interface BmsService {

    List<Bms> selectBmsListByPointId(Integer PointId);

    Bms selectFaultCounts();

    int deleteById(Integer id);

    int deleteByPointId(Integer PointId);

    int addOne(Bms bms);

}
