package cn.ruishan.iac.service;


import cn.ruishan.common.base.service.IBaseService;
import cn.ruishan.common.web.ApiResult;
import cn.ruishan.common.web.PageParam;
import cn.ruishan.iac.entity.IacUser;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
public interface IIacUserService extends IBaseService<IacUser> {

    /**
     * @desc: 关联分页查询
     * @param page
     * @author: longgang.lei
     * @time: 2021-06-22 18:02
     */
    List<IacUser> listPage(PageParam<IacUser> page);

    ApiResult login(String username, ApiResult apiResult);
}
