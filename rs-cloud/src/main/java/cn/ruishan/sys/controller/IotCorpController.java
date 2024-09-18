package cn.ruishan.sys.controller;


import cn.hutool.core.util.StrUtil;
import cn.ruishan.common.Constants;
import cn.ruishan.common.annotation.ApiPageParam;
import cn.ruishan.common.base.controller.BaseController;
import cn.ruishan.common.web.JsonResult;
import cn.ruishan.common.web.PageParam;
import cn.ruishan.common.web.PageResult;
import cn.ruishan.sys.entity.IotCorp;
import cn.ruishan.sys.service.IIotCorpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 企业表 前端控制器
 * </p>
 */
@Api(value = "企业管理Controller", tags = "企业管理接口")
@RestController
@RequestMapping("iot/corp")
public class IotCorpController extends BaseController {

    @Autowired
    private IIotCorpService iotCorpService;

    @ApiPageParam
    @ApiOperation(value = "查询所有企业")
    @PreAuthorize("hasAuthority('get:iot/corp')")
    @GetMapping()
    public PageResult<IotCorp> listPage(HttpServletRequest request) {
        PageParam<IotCorp> pageParam = new PageParam<>(request);
        pageParam.setDefaultOrderByCreateTime();
        return new PageResult<>(iotCorpService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
    }

    @ApiOperation(value = "添加企业")
    @PreAuthorize("hasAuthority('post:iot/corp')")
    @PostMapping()
    public JsonResult add(IotCorp corp) {
        // 不填密码，默认密码
        if(StrUtil.isBlank(corp.getPassword())){
            corp.setPassword(Constants.INITIAL_PASSWORD);
        }

        // 自己设密码
        corp.setPassword(passwordEncoder.encode(corp.getPassword()));

        corp.setStatus(Constants.DEFAULT_STATUS);
        if (iotCorpService.save(corp)) {
            return JsonResult.ok("添加成功");
        }

        return JsonResult.failure("添加失败");
    }

    @ApiOperation(value = "修改企业")
    @PreAuthorize("hasAuthority('put:iot/corp')")
    @PutMapping()
    public JsonResult update(IotCorp corp) {
        if(StrUtil.isBlank(corp.getPassword())) {
            corp.setPassword(null);
        }else{
            corp.setPassword(passwordEncoder.encode(corp.getPassword()));
        }
        if (iotCorpService.updateById(corp)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.failure("修改失败");
    }

    @ApiOperation(value = "修改企业状态")
    @PreAuthorize("hasAuthority('put:iot/corp')")
    @PutMapping("state")
    public JsonResult updateState(Integer corpId, Boolean state) {
        if (corpId == null) {
            return JsonResult.failure("未获取到企业ID");
        }
        if (state == null) {
            return JsonResult.failure("状态值不正确");
        }
        IotCorp corp = new IotCorp();
        corp.setCorpId(corpId);
        corp.setStatus(state);
        if (iotCorpService.updateById(corp)) {
            return JsonResult.ok();
        }
        return JsonResult.failure();
    }

    @ApiOperation(value = "修改密码")
    @PreAuthorize("hasAuthority('put:iot/corp/pwd')")
    @PutMapping("pwd")
    public JsonResult updatePwd(Integer corpId, String oldPwd, String newPwd) {
        if (StrUtil.isBlank(oldPwd) || StrUtil.isBlank(newPwd)) {
            return JsonResult.failure("旧密码和新密码不能为空");
        }

        if (corpId == null) {
            return JsonResult.failure("未获取到企业ID");
        }

        IotCorp entity = iotCorpService.getById(corpId);
        if(entity == null) {
            return JsonResult.failure("未找到此企业");
        }
        if (!passwordEncoder.matches(oldPwd, entity.getPassword())) {
            return JsonResult.failure("原密码输入不正确");
        }

        IotCorp corp = new IotCorp();
        corp.setCorpId(corpId);
        corp.setPassword(passwordEncoder.encode(newPwd));
        if (iotCorpService.updateById(corp)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.failure("修改失败");
    }

    @ApiOperation(value = "删除企业")
    @PreAuthorize("hasAuthority('delete:iot/corp/{corpId}')")
    @DeleteMapping("{corpId}")
    public JsonResult delete(@PathVariable("corpId") Integer corpId) {
        if (iotCorpService.removeById(corpId)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.failure("删除失败");
    }

    @ApiOperation(value = "获取企业下拉列表")
    @GetMapping("select")
    public JsonResult select() {

        return JsonResult.ok().setData(iotCorpService.select("corp_id"));
    }
}
