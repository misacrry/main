package cn.ruishan.iac.service;


import cn.ruishan.common.base.service.IBaseService;
import cn.ruishan.iac.entity.Substation;

import java.util.List;

public interface SubstationService extends IBaseService<Substation> {
    //获取用户关联的储能站
    List<Substation> selectUserSubstation();

    //配置储能站关联用户
    Boolean setSubstationRelUser(Integer substationId, List<Integer> userIds);
}
