package com.example.cloud.service.impl;

import com.example.cloud.mapper.PcsMapper;
import com.example.cloud.model.Pcs;
import com.example.cloud.service.PcsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class PcsServiceImpl implements PcsService {

    @Resource
    PcsMapper pcsMapper;

    @Override
    public List<Pcs> selectList() {
        return pcsMapper.selectList();
    }

    @Override
    public Pcs faultCounts() {
        return pcsMapper.faultCounts();
    }

    @Override
    public List<Pcs> selectPcsListByPointId(Integer pointId) {
        return pcsMapper.selectPcsListByPointId(pointId);
    }

    @Override
    public int deleteById(Integer id) {
        return pcsMapper.deleteById(id);
    }

    @Override
    public int deleteByPointId(Integer pointId) {
        return pcsMapper.deleteByPointId(pointId);
    }

    @Override
    public int addOne(Pcs pcs) {
        return pcsMapper.addOne(pcs);
    }
}
