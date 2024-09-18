package cn.ruishan.iac.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.ruishan.common.base.service.impl.BaseServiceImpl;
import cn.ruishan.iac.entity.StrategyChargeDischarge;
import cn.ruishan.iac.entity.Substation;
import cn.ruishan.iac.mapper.StrategyChargeDischargeMapper;
import cn.ruishan.iac.mapper.SubstationMapper;
import cn.ruishan.iac.service.StrategyChargeDischargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategyChargeDischargeServiceImpl extends BaseServiceImpl<StrategyChargeDischargeMapper, StrategyChargeDischarge> implements StrategyChargeDischargeService {
    @Autowired
    private StrategyChargeDischargeMapper strategyChargeDischargeMapper;
    @Autowired
    private SubstationMapper substationMapper;
    @Override
    public boolean updateMonth(List<StrategyChargeDischarge> strategyChargeDischarges) {
        if (CollUtil.isNotEmpty(strategyChargeDischarges)){
            Integer substationId = 0;
            for (StrategyChargeDischarge strategyChargeDischarge : strategyChargeDischarges){
                substationId = strategyChargeDischarge.getSubstationId();
                if(strategyChargeDischargeMapper.updateById(strategyChargeDischarge) != 1){
                    return false;
                }
            }
            if (substationId != 0){
                Substation substation = substationMapper.selectById(substationId);
                substation.setPresetStrategyVer(DateUtil.current());
                substationMapper.updateById(substation);
            }
        }
        return true;
    }
}
