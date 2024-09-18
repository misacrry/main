package cn.ruishan.iac.service;


import cn.ruishan.common.base.service.IBaseService;
import cn.ruishan.iac.entity.StrategySchedule;

import java.util.List;
import java.util.Map;

public interface StrategyScheduleService extends IBaseService<StrategySchedule> {
    Map<String, Object> selectScheduleList(Integer strategyId);
}
