package com.example.cloud.mapper;

import com.example.cloud.model.GridConnectionPoint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GridConnectionPointMapper {

    List<GridConnectionPoint> selectList();

    List<GridConnectionPoint> selectListBySubId(Integer subId);

    int deleteBySubId(Integer subId);

    int deleteById(Integer id);

    int addOne(GridConnectionPoint gridConnectionPoint);
}
