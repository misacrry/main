package com.example.cloud.mapper;

import com.example.cloud.model.Substation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubstationMapper {
    Substation runningStatus();
}
