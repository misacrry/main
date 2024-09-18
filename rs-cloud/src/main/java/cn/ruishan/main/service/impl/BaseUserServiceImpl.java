package cn.ruishan.main.service.impl;


import cn.ruishan.common.base.service.impl.BaseServiceImpl;
import cn.ruishan.main.entity.BaseUser;
import cn.ruishan.main.mapper.BaseUserMapper;
import cn.ruishan.main.service.IBaseUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基本用户表 服务实现类
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
@Service
public class BaseUserServiceImpl extends BaseServiceImpl<BaseUserMapper, BaseUser> implements IBaseUserService {

}
