package cn.ruishan.iac.service;


import cn.ruishan.common.base.service.IBaseService;
import cn.ruishan.iac.entity.StrategyChargeDischarge;

import java.util.List;
import java.util.Map;

public interface StrategyChargeDischargeService extends IBaseService<StrategyChargeDischarge> {
    boolean updateMonth(List<StrategyChargeDischarge> strategyChargeDischarges);
}
