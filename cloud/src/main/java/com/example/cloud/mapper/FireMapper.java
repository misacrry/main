package com.example.cloud.mapper;

import com.example.cloud.model.FireControl;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FireMapper {

    List<FireControl> selectList();

    FireControl faultCounts();

    int addOne(FireControl fireControl);

    int delete(Integer id);
}
