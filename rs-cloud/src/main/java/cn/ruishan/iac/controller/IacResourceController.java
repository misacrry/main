package cn.ruishan.iac.controller;

import cn.hutool.core.util.StrUtil;
import cn.ruishan.common.annotation.ApiPageParam;
import cn.ruishan.common.web.JsonResult;
import cn.ruishan.common.web.PageResult;
import cn.ruishan.iac.entity.IacResource;
import cn.ruishan.iac.service.IacResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "工商业资源Controller", tags = "工商业资源接口")
@RestController
@RequestMapping("iac/resource")
public class IacResourceController {

    @Autowired
    private IacResourceService iacResourceService;

    @ApiPageParam
    @ApiOperation(value = "查询所有资源")
    @GetMapping()
    public PageResult<IacResource> list(String keyword) {
        QueryWrapper<IacResource> queryWrapper = new QueryWrapper<IacResource>();
        if (StrUtil.isNotBlank(keyword)) {
            queryWrapper.like("name", keyword).or().like("authority", keyword);
        }
        queryWrapper.orderByAsc("sort");
        List<IacResource> list = iacResourceService.list(queryWrapper);
        return new PageResult<IacResource>(list, list.size());
    }

    @ApiOperation(value = "添加资源")
    @PostMapping()
    public JsonResult addType(IacResource resource) {
        if (iacResourceService.save(resource)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.failure("添加失败");
    }

    @ApiOperation(value = "修改资源")
    @PutMapping()
    public JsonResult updateType(IacResource resource) {
        if (iacResourceService.updateById(resource)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.failure("修改失败");
    }

    @ApiOperation(value = "删除资源")
    @DeleteMapping("{resourceId}")
    public JsonResult delete(@PathVariable("resourceId") Integer resourceId) {
        if (iacResourceService.removeById(resourceId)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.failure("删除失败");
    }

    @ApiOperation(value = "获取资源下拉树形列表")
    @GetMapping("selectTree")
    public JsonResult selectTree(Integer type, Boolean showRoot, Integer selectId) {
        return JsonResult.ok().setData(iacResourceService.selectTree(type, showRoot, selectId));
    }

    @ApiOperation(value = "获取角色对应的权限列表")
    @GetMapping("role/{roleId}")
    public JsonResult role(@PathVariable("roleId") Integer roleId) {

        return JsonResult.ok().setData(iacResourceService.role(roleId));
    }
}
