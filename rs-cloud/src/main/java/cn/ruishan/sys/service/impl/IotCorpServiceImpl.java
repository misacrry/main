package cn.ruishan.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.ruishan.common.base.service.impl.BaseServiceImpl;
import cn.ruishan.common.security.LoginUtil;
import cn.ruishan.sys.entity.IotCorp;
import cn.ruishan.sys.mapper.IotCorpMapper;
import cn.ruishan.sys.service.IIotCorpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 * 企业表 服务实现类
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
@Service
public class IotCorpServiceImpl extends BaseServiceImpl<IotCorpMapper, IotCorp> implements IIotCorpService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        IotCorp entity = new IotCorp();
        entity.setCorpId((Integer) id);
        entity.setDeleteId(LoginUtil.getLoginUserId());
        entity.setDeleteTime(DateUtil.current());
        super.updateById(entity);
        return super.removeById(id);
    }
}
