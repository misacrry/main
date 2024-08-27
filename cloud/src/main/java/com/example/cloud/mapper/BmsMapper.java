package com.example.cloud.mapper;

import com.example.cloud.model.Bms;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BmsMapper {

    List<Bms> selectBmsListByPointId(Integer PointId);

    Bms selectFaultCounts();

    int deleteById(Integer id);

    int deleteByPointId(Integer PointId);

    int addOne(Bms bms);


}
