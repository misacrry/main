package cn.ruishan.iac.mapper;


import cn.ruishan.common.web.PageParam;
import cn.ruishan.iac.entity.IacUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
public interface IacUserMapper extends BaseMapper<IacUser> {

    /**
     * 分页查询
     */
    List<IacUser> listPage(@Param("page") PageParam<IacUser> page);

}
