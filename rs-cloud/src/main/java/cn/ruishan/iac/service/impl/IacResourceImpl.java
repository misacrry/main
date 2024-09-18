package cn.ruishan.iac.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.ruishan.common.base.service.impl.BaseServiceImpl;
import cn.ruishan.common.model.ZTree;
import cn.ruishan.common.security.LoginUtil;
import cn.ruishan.iac.entity.IacRelRoleResource;
import cn.ruishan.iac.entity.IacResource;
import cn.ruishan.iac.mapper.IacRelRoleResourceMapper;
import cn.ruishan.iac.mapper.IacResourceMapper;
import cn.ruishan.iac.service.IacResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IacResourceImpl extends BaseServiceImpl<IacResourceMapper, IacResource> implements IacResourceService {

    @Autowired
    private IacRelRoleResourceMapper iacRelRoleResourceMapper;

    @Override
    public List<Map<String, Object>> selectTree(Integer type, Boolean showRoot, Integer selectId) {
        QueryWrapper<IacResource> queryWrapper = new QueryWrapper<IacResource>();
        queryWrapper.select("resource_id", "parent_id", "name");
        if(type != null) {
            queryWrapper.eq("type", type);
        }
        queryWrapper.orderByAsc("sort");
        List<IacResource> resources = this.list(queryWrapper);

        Integer parentId = -1;

        if(showRoot) {
            IacResource resource = new IacResource();
            resource.setResourceId(-1);
            resource.setName("顶级菜单");
            resource.setParentId(-2);
            resources.add(resource);

            parentId = -2;
        }

        return this.createResourceTree(resources, parentId, selectId);
    }

    /**
     * 递归转化树形菜单
     */
    private List<Map<String, Object>> createResourceTree(List<IacResource> resources, Integer parentId, Integer selectId) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < resources.size(); i++) {
            IacResource temp = resources.get(i);
            // 父ID一致，类型不为按钮
            if (parentId.equals(temp.getParentId())) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", temp.getName());
                map.put("value", temp.getResourceId());
                // 选中
                if(selectId != null && selectId.equals(temp.getResourceId())) {
                    map.put("selected", true);
                }
                map.put("children", createResourceTree(resources, temp.getResourceId(), selectId));
                list.add(map);
            }
        }

        return list;
    }

    @Override
    public List<ZTree> role(Integer roleId) {
        List<IacRelRoleResource> devopsRelRoleResources = iacRelRoleResourceMapper.selectList(new QueryWrapper<IacRelRoleResource>().eq("role_id", roleId));

        List<Integer> resourceIds = CollUtil.newArrayList();
        if(CollUtil.isNotEmpty(devopsRelRoleResources)) {
            for (IacRelRoleResource devopsRelRoleResource : devopsRelRoleResources) {
                resourceIds.add(devopsRelRoleResource.getResourceId());
            }
        }

        QueryWrapper<IacResource> queryWrapper = new QueryWrapper<IacResource>();
        queryWrapper.select("resource_id", "parent_id", "name");
        queryWrapper.orderByAsc("sort");
        List<IacResource> IacResources = this.list(queryWrapper);

        List<ZTree> zTrees = CollUtil.newArrayList();

        if(CollUtil.isNotEmpty(IacResources)) {
            for (IacResource IacResource : IacResources) {
                ZTree zTree = new ZTree();
                zTree.setId(IacResource.getResourceId());
                zTree.setName(IacResource.getName());
                zTree.setParentId(IacResource.getParentId());
                if(CollUtil.contains(resourceIds, zTree.getId())) {
                    zTree.setChecked(true);
                }

                zTrees.add(zTree);
            }
        }

        return zTrees;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        IacResource entity = new IacResource();
        entity.setResourceId((Integer) id);
        entity.setDeleteId(LoginUtil.getLoginUserId());
        entity.setDeleteTime(DateUtil.current());
        super.updateById(entity);
        return super.removeById(id);
    }
}
