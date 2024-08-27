package com.example.cloud.mapper;

import com.example.cloud.model.Substation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubstationMapper {

    int addSub(Substation substation);

    int delSubById(Integer id);

    List<Substation> selectSubList();
}
