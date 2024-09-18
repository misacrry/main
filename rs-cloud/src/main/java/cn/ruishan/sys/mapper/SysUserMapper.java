package cn.ruishan.sys.mapper;

import cn.ruishan.common.base.mapper.CrudMapper;
import cn.ruishan.common.web.PageParam;
import cn.ruishan.sys.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends CrudMapper<SysUser> {

    /**
     * 分页查询
     */
    List<SysUser> listPage(@Param("page") PageParam<SysUser> page);
}
