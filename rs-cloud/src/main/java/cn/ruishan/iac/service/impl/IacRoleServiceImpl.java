package cn.ruishan.iac.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.ruishan.common.base.service.impl.BaseServiceImpl;
import cn.ruishan.common.exception.BusinessException;
import cn.ruishan.common.security.LoginUtil;
import cn.ruishan.iac.entity.IacRelRoleResource;
import cn.ruishan.iac.entity.IacRole;
import cn.ruishan.iac.entity.IacUser;
import cn.ruishan.iac.mapper.IacRelRoleResourceMapper;
import cn.ruishan.iac.mapper.IacRelRoleUserMapper;
import cn.ruishan.iac.mapper.IacRoleMapper;
import cn.ruishan.iac.service.IacRoleService;
import cn.ruishan.main.entity.BaseUser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 */
@Service
public class IacRoleServiceImpl extends BaseServiceImpl<IacRoleMapper, IacRole> implements IacRoleService {

    @Autowired
    private IacRelRoleResourceMapper iacRelRoleResourceMapper;

    @Autowired
    private IacRoleMapper iacRoleMapper;

    @Transactional
    @Override
    public boolean auth(Integer roleId, List<Integer> authIds) {

        if(roleId != null && CollUtil.isNotEmpty(authIds)) {
            iacRelRoleResourceMapper.delete(new UpdateWrapper<IacRelRoleResource>().eq("role_id", roleId));
            // 根据角色ID删除所有权限
            int count = 0;
            for (Integer resourceId : authIds) {
                IacRelRoleResource entity = new IacRelRoleResource();
                entity.setRoleId(roleId);
                entity.setResourceId(resourceId);
                count += iacRelRoleResourceMapper.insert(entity);
            }

            if(count < authIds.size()) {
                throw new BusinessException("授权失败");
            }
        }
        return true;
    }

    @Override
    public List<Map<String, Object>> select(String idStr, Integer corpId) {

        List<Map<String, Object>> maps = baseMapper.selectMaps(new QueryWrapper<IacRole>()
                .select(idStr)
                .eq("corp_id", corpId));
        List<Map<String, Object>> resultList = new ArrayList<>();
        if(CollUtil.isNotEmpty(maps)) {
            for (Map<String, Object> map : maps) {
                Integer id = Integer.parseInt(map.get(idStr).toString());
                IacRole iacRole = iacRoleMapper.selectById(id);
                if (iacRole != null){
                    map.put("name", iacRole.getName());
                    map.put("value", map.remove(idStr));
                    resultList.add(map);
                }
            }
        }

        return resultList;
    }
}
