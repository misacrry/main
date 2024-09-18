package cn.ruishan.iac.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.ruishan.common.base.service.impl.BaseServiceImpl;
import cn.ruishan.common.security.LoginUtil;
import cn.ruishan.iac.entity.GridConnectionPoint;
import cn.ruishan.iac.entity.RelUserSubstation;
import cn.ruishan.iac.entity.StrategyChargeDischarge;
import cn.ruishan.iac.entity.Substation;
import cn.ruishan.iac.mapper.GridConnectionPointMapper;
import cn.ruishan.iac.mapper.RelUserSubstationMapper;
import cn.ruishan.iac.mapper.StrategyChargeDischargeMapper;
import cn.ruishan.iac.mapper.SubstationMapper;
import cn.ruishan.iac.service.SubstationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.mail.imap.protocol.BODY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Service
public class SubstationServiceImpl extends BaseServiceImpl<SubstationMapper, Substation> implements SubstationService {

    @Autowired
    private RelUserSubstationMapper relUserSubstationMapper;

    @Autowired
    private SubstationMapper substationMapper;

    @Autowired
    private StrategyChargeDischargeMapper strategyChargeDischargeMapper;

    @Autowired
    private GridConnectionPointMapper gridConnectionPointMapper;

    @Override
    public List<Substation> selectUserSubstation() {
        List<RelUserSubstation> rels = relUserSubstationMapper.selectList(new QueryWrapper<RelUserSubstation>().eq("user_id",LoginUtil.getLoginUserId()));
        List<Integer> substationIds = CollUtil.newArrayList();
        if (CollUtil.isNotEmpty(rels)) {
            for (RelUserSubstation relUserSubstation : rels) {
                substationIds.add(relUserSubstation.getSubstationId());
            }
        }
        if (CollUtil.isNotEmpty(substationIds)) {
            return substationMapper.selectBatchIds(substationIds);
        }

        return new ArrayList<>();
    }

    @Override
    public boolean save(Substation entity) {
        boolean result = super.save(entity);
        if (result){
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),0,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),0,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),0,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),0,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),0,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),0,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),0,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),0,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),0,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),0,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),1,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),1,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),1,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),1,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),1,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),1,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),1,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),1,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),1,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),1,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),2,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),2,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),2,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),2,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),2,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),2,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),2,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),2,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),2,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),2,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),3,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),3,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),3,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),3,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),3,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),3,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),3,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),3,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),3,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),3,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),4,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),4,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),4,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),4,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),4,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),4,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),4,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),4,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),4,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),4,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),5,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),5,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),5,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),5,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),5,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),5,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),5,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),5,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),5,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),5,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),6,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),6,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),6,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),6,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),6,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),6,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),6,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),6,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),6,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),6,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),7,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),7,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),7,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),7,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),7,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),7,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),7,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),7,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),7,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),7,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),8,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),8,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),8,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),8,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),8,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),8,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),8,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),8,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),8,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),8,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),9,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),9,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),9,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),9,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),9,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),9,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),9,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),9,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),9,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),9,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),10,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),10,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),10,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),10,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),10,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),10,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),10,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),10,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),10,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),10,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),11,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),11,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),11,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),11,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),11,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),11,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),11,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),11,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),11,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),11,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),12,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),12,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),12,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),12,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),12,false,"00:00:00","00:00:00",true,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),12,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),12,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),12,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),12,false,"00:00:00","00:00:00",false,0));
            strategyChargeDischargeMapper.insert(createStrategyData(entity.getId(),12,false,"00:00:00","00:00:00",false,0));

            relUserSubstationMapper.insert(new RelUserSubstation(LoginUtil.getLoginUserId(),entity.getId()));
        }
        return result;
    }

    //生成区域参数
    public StrategyChargeDischarge createStrategyData(Integer substationId, Integer month, Boolean enable, String start, String end, Boolean charge, double value){
        StrategyChargeDischarge strategyChargeDischarge = new StrategyChargeDischarge();
        strategyChargeDischarge.setSubstationId(substationId);
        strategyChargeDischarge.setMonth(month);
        strategyChargeDischarge.setEnable(enable);
        strategyChargeDischarge.setStart(start);
        strategyChargeDischarge.setEnd(end);
        strategyChargeDischarge.setCharge(charge);
        strategyChargeDischarge.setValue(value);
        return strategyChargeDischarge;
    }

    @Override
    public boolean removeById(Serializable id) {
        boolean result = super.removeById(id);
        if (result){
            strategyChargeDischargeMapper.delete(new LambdaQueryWrapper<StrategyChargeDischarge>().eq(StrategyChargeDischarge::getSubstationId,id));
//            gridConnectionPointMapper.delete(new LambdaQueryWrapper<GridConnectionPoint>().eq(GridConnectionPoint::getSubstationId, id));
        }
        return result;
    }

    @Override
    public Boolean setSubstationRelUser(Integer substationId, List<Integer> userIds) {
        relUserSubstationMapper.delete(new LambdaQueryWrapper<RelUserSubstation>().eq(RelUserSubstation::getSubstationId, substationId));
        if (CollUtil.isNotEmpty(userIds)){
            for (Integer userId: userIds){
                relUserSubstationMapper.insert(new RelUserSubstation(userId, substationId));
            }
        }
        return true;
    }
}
