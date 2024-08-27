package com.example.cloud.service;

import com.example.cloud.model.FireControl;

import java.util.List;

public interface FireService {

    List<FireControl> selectList();

    FireControl faultCounts();

    int addOne(FireControl fireControl);

    int delete(Integer id);

}
