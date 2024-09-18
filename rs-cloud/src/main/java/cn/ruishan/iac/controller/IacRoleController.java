package cn.ruishan.iac.controller;


import cn.hutool.json.JSONUtil;
import cn.ruishan.common.annotation.ApiPageParam;
import cn.ruishan.common.base.controller.BaseController;
import cn.ruishan.common.web.JsonResult;
import cn.ruishan.common.web.PageParam;
import cn.ruishan.common.web.PageResult;
import cn.ruishan.iac.entity.IacRole;
import cn.ruishan.iac.service.IacRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 */
@Api(value = "角色管理Controller", tags = "角色管理接口")
@RestController
@RequestMapping("iac/role")
public class IacRoleController extends BaseController {

    @Autowired
    private IacRoleService iacRoleService;

    @ApiPageParam
    @ApiOperation(value = "查询所有角色")
    @GetMapping()
    public PageResult<IacRole> listPage(HttpServletRequest request) {
        PageParam<IacRole> pageParam = new PageParam<IacRole>(request);
        return new PageResult<IacRole>(iacRoleService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
    }

    @ApiOperation(value = "添加角色")
    @PostMapping()
    public JsonResult add(IacRole role) {
        if (iacRoleService.save(role)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.failure("添加失败");
    }

    @ApiOperation(value = "修改角色")
    @PutMapping()
    public JsonResult update(IacRole role) {
        if (iacRoleService.updateById(role)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.failure("修改失败");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("{roleId}")
    public JsonResult delete(@PathVariable("roleId") Integer roleId) {
        if (iacRoleService.removeById(roleId)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.failure("删除失败");
    }

    @ApiOperation(value = "获取角色下拉列表")
    @GetMapping("select")
    public JsonResult select(Integer corpId) {
        return JsonResult.ok().setData(iacRoleService.select("role_id", corpId));
    }

    @ApiOperation(value = "角色授权")
    @PostMapping("auth")
    public JsonResult auth(Integer id, String authIds) {
        List<Integer> authIdList = JSONUtil.toList(JSONUtil.parseArray(authIds), Integer.class);
        if (iacRoleService.auth(id, authIdList)) {
            return JsonResult.ok("授权成功");
        }
        return JsonResult.failure("授权失败");
    }
}
