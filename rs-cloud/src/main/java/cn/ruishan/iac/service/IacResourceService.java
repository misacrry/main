package cn.ruishan.iac.service;

import cn.ruishan.common.base.service.IBaseService;
import cn.ruishan.common.model.ZTree;
import cn.ruishan.iac.entity.IacResource;

import java.util.List;
import java.util.Map;

public interface IacResourceService extends IBaseService<IacResource> {
    /**
     * 获取资源下拉列表(Tree格式)
     * @param type 类型
     * @param showRoot 是否显示根节点
     * @param selectId 选中项ID
     * @return
     */
    List<Map<String, Object>> selectTree(Integer type, Boolean showRoot, Integer selectId);

    /**
     * 获取角色对应的权限树(ZTree格式)
     * @param roleId
     * @return
     */
    List<ZTree> role(Integer roleId);
}
