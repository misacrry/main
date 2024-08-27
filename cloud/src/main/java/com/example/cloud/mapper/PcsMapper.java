package com.example.cloud.mapper;

import com.example.cloud.model.Pcs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PcsMapper {

    List<Pcs> selectList();

    Pcs faultCounts();

    List<Pcs> selectPcsListByPointId(Integer pointId);

    int deleteById(Integer id);

    int deleteByPointId(Integer pointId);

    int addOne(Pcs pcs);

}
