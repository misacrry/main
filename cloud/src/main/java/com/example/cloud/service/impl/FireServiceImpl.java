package com.example.cloud.service.impl;

import com.example.cloud.mapper.FireMapper;
import com.example.cloud.model.FireControl;
import com.example.cloud.service.FireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class FireServiceImpl implements FireService {

    @Resource
    private FireMapper fireMapper;

    @Override
    public List<FireControl> selectList() {
        return fireMapper.selectList();
    }

    @Override
    public FireControl faultCounts() {
        return fireMapper.faultCounts();
    }

    @Override
    public int addOne(FireControl fireControl) {
        return fireMapper.addOne(fireControl);
    }

    @Override
    public int delete(Integer id) {
        return fireMapper.delete(id);
    }
}
