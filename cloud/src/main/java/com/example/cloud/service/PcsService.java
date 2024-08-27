package com.example.cloud.service;

import com.example.cloud.model.Pcs;

import java.util.List;

public interface PcsService {

    List<Pcs> selectList();

    Pcs faultCounts();

    List<Pcs> selectPcsListByPointId(Integer pointId);

    int deleteById(Integer id);

    int deleteByPointId(Integer pointId);

    int addOne(Pcs pcs);


}
