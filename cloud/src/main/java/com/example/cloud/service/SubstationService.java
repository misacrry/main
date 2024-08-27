package com.example.cloud.service;

import com.example.cloud.model.Substation;

import java.util.List;

public interface SubstationService {

    int addSub(Substation substation);

    int delSubById(Integer id);

    List<Substation> selectSubList();

}
