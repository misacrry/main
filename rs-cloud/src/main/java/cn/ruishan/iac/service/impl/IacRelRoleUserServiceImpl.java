package cn.ruishan.iac.service.impl;

import cn.ruishan.common.base.service.impl.BaseServiceImpl;
import cn.ruishan.iac.entity.IacRelRoleUser;
import cn.ruishan.iac.mapper.IacRelRoleUserMapper;
import cn.ruishan.iac.service.IacRelRoleUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色和用户关联表 服务实现类
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
@Service
public class IacRelRoleUserServiceImpl extends BaseServiceImpl<IacRelRoleUserMapper, IacRelRoleUser> implements IacRelRoleUserService {

}
