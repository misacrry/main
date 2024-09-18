package cn.ruishan.iac.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.ruishan.common.base.service.impl.BaseServiceImpl;
import cn.ruishan.common.exception.BusinessException;
import cn.ruishan.common.security.LoginUtil;
import cn.ruishan.common.web.ApiResult;
import cn.ruishan.common.web.PageParam;
import cn.ruishan.iac.entity.IacRelRoleUser;
import cn.ruishan.iac.entity.IacUser;
import cn.ruishan.iac.mapper.IacRelRoleUserMapper;
import cn.ruishan.iac.mapper.IacRoleMapper;
import cn.ruishan.iac.mapper.IacUserMapper;
import cn.ruishan.iac.service.IacUserService;
import cn.ruishan.main.entity.BaseUser;
import cn.ruishan.main.mapper.BaseUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Service
public class IacUserServiceImpl extends BaseServiceImpl<IacUserMapper, IacUser> implements IacUserService {

    @Autowired
    private BaseUserMapper baseUserMapper;
    @Autowired
    private IacRelRoleUserMapper iacRelRoleUserMapper;
    @Autowired
    private IacRoleMapper iacRoleMapper;

    @Override
    public List<IacUser> listPage(PageParam<IacUser> page) {
        List<IacUser> userList = baseMapper.listPage(page);
        if(CollUtil.isNotEmpty(userList)) {
            for (IacUser user : userList) {
                List<IacRelRoleUser> userRoles = iacRelRoleUserMapper.selectList(new QueryWrapper<IacRelRoleUser>().eq("user_id", user.getUserId()));

                List<Integer> roleIds = CollUtil.newArrayList();
                if (CollUtil.isNotEmpty(userRoles)) {
                    for (IacRelRoleUser relRoleUsr : userRoles) {
                        roleIds.add(relRoleUsr.getRoleId());
                    }
                }

                if(CollUtil.isNotEmpty(roleIds)) {
                    user.setRoleIds(roleIds);
                    user.setRoles(iacRoleMapper.selectBatchIds(roleIds));
                }
            }
        }
        return userList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        BaseUser entity = new BaseUser();
        entity.setUserId((Integer) id)
                .setDeleteId(LoginUtil.getLoginUserId())
                .setDeleteTime(DateUtil.current());
        baseUserMapper.updateById(entity);

        return SqlHelper.retBool(baseUserMapper.deleteById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(IacUser entity) {

        // 基础用户信息
        BaseUser baseUser = baseUserMapper.selectOne(new QueryWrapper<BaseUser>().eq("loginname", entity.getLoginname()));
        if (!BeanUtil.isEmpty(baseUser)) {
            throw new BusinessException("账号已经存在");
        }

        baseUser = entity.buildBaseUser();
        baseUserMapper.insert(baseUser);
        entity.setUserId(baseUser.getUserId());

        // 添加角色关联
        List<Integer> roleIds = entity.getRoleIds();
        if(CollUtil.isNotEmpty(roleIds)) {
            int count = 0;
            for (Integer roleId : roleIds) {
                count += iacRelRoleUserMapper.insert(new IacRelRoleUser()
                        .setUserId(entity.getUserId())
                        .setRoleId(roleId));
            }

            if (count < roleIds.size()) {
                throw new BusinessException("添加失败");
            }
        }

        return super.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(IacUser entity) {

        // 更新基础用户信息
        entity.setLoginname(null);  // 账号不能修改
        baseUserMapper.updateById(entity.buildBaseUser());

        // 更新本信息
        super.updateById(entity);

        // 更新角色关联
        List<Integer> roleIds = entity.getRoleIds();
        if(CollUtil.isNotEmpty(roleIds)) {

            iacRelRoleUserMapper.delete(new UpdateWrapper<IacRelRoleUser>().eq("user_id", entity.getUserId()));

            int count = 0;
            for (Integer roleId : roleIds) {
                count += iacRelRoleUserMapper.insert(new IacRelRoleUser()
                        .setUserId(entity.getUserId())
                        .setRoleId(roleId));
            }

            if (count < roleIds.size()) {
                throw new BusinessException("修改失败");
            }
        }
        return true;
    }


    @Override
    public List<Map<String, Object>> select(String idStr, Integer corpId) {

        List<Map<String, Object>> maps = baseMapper.selectMaps(new QueryWrapper<IacUser>()
                .select(idStr)
                .eq("corp_id", corpId));
        List<Map<String, Object>> resultList = new ArrayList<>();
        if(CollUtil.isNotEmpty(maps)) {
            for (Map<String, Object> map : maps) {
                Integer id = Integer.parseInt(map.get(idStr).toString());
                BaseUser baseUser = baseUserMapper.selectById(id);
                if (baseUser != null){
                    map.put("name", baseUser.getUsername());
                    map.put("value", map.remove(idStr));
                    resultList.add(map);
                }
            }
        }

        return resultList;
    }
}
