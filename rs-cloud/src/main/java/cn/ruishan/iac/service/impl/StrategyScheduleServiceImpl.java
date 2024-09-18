package cn.ruishan.iac.service.impl;


import cn.hutool.json.JSONObject;
import cn.ruishan.common.base.service.impl.BaseServiceImpl;
import cn.ruishan.iac.entity.StrategySchedule;
import cn.ruishan.iac.mapper.StrategyScheduleMapper;
import cn.ruishan.iac.service.StrategyScheduleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.lang.Collections;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StrategyScheduleServiceImpl extends BaseServiceImpl<StrategyScheduleMapper, StrategySchedule> implements StrategyScheduleService {
    @Override
    public Map<String, Object> selectScheduleList(Integer strategyId) {
        StrategySchedule strategySchedule = super.getOne(new QueryWrapper<StrategySchedule>().eq("strategy_id",strategyId));
        Map<String, Object> res = new HashMap<>();
        res.put("id",strategySchedule.getId());
        res.put("strategy_id", strategyId);
        JSONObject schedule =  new JSONObject(strategySchedule.getSchedule());
        schedule.put("id",strategySchedule.getId());
        schedule.put("strategy_id", strategyId);
        return schedule;
    }
}
