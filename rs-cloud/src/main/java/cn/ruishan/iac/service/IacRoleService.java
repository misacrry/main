package cn.ruishan.iac.service;

import cn.ruishan.common.base.service.IBaseService;
import cn.ruishan.iac.entity.IacRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 */
public interface IacRoleService extends IBaseService<IacRole> {

    /**
     * 运维资源授权
     */
    boolean auth(Integer roleId, List<Integer> authIds);

    List<Map<String, Object>> select(String idStr, Integer corpId);
}
