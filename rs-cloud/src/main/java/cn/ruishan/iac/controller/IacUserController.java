package cn.ruishan.iac.controller;

import cn.hutool.core.util.StrUtil;
import cn.ruishan.common.annotation.ApiPageParam;
import cn.ruishan.common.base.controller.BaseController;
import cn.ruishan.common.web.JsonResult;
import cn.ruishan.common.web.PageParam;
import cn.ruishan.common.web.PageResult;
import cn.ruishan.iac.entity.IacUser;
import cn.ruishan.iac.service.IacUserService;
import cn.ruishan.main.entity.BaseUser;
import cn.ruishan.main.mapper.BaseUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 */
@Api(value = "用户管理Controller", tags = "用户管理接口")
@RestController
@RequestMapping("iac/user")
public class IacUserController extends BaseController {

    @Autowired
    private IacUserService iacUserService;
    @Autowired
    private BaseUserMapper baseUserMapper;

    @ApiPageParam
    @ApiOperation(value = "查询所有用户")
    @GetMapping()
    public PageResult<IacUser> listPage(HttpServletRequest request) {
        PageParam<IacUser> pageParam = new PageParam<>(request);
        pageParam.setDefaultOrderByCreateTime();
        return new PageResult<IacUser>(iacUserService.listPage(pageParam), pageParam.getTotal());
    }

    @ApiOperation(value = "添加用户")
    @PostMapping()
    public JsonResult add(IacUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (iacUserService.save(user)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.failure("添加失败");
    }

    @ApiOperation(value = "修改用户")
    @PutMapping()
    public JsonResult update(IacUser user) {

        if(StrUtil.isBlank(user.getPassword())){
            user.setPassword(null);
        }else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (iacUserService.updateById(user)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.failure("修改失败");
    }

    @ApiOperation(value = "修改用户状态")
    @PutMapping("state")
    public JsonResult updateState(Integer userId, Boolean state) {
        if (userId == null) {
            return JsonResult.failure("未获取到用户ID");
        }
        if (state == null) {
            return JsonResult.failure("状态值不正确");
        }
        IacUser user = new IacUser();
        user.setUserId(userId);
        user.setStatus(!state);
        if (iacUserService.updateById(user)) {
            return JsonResult.ok();
        }
        return JsonResult.failure();
    }

    @ApiOperation(value = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户ID", dataTypeClass=Integer.class, paramType = "form"),
            @ApiImplicitParam(name="oldPwd", value="旧密码", dataTypeClass=String.class, paramType = "form"),
            @ApiImplicitParam(name="newPwd", value="新密码", dataTypeClass=String.class, paramType = "form")
    })
    @PutMapping("pwd")
    public JsonResult updatePwd(Integer userId, String oldPwd, String newPwd) {
        if (StrUtil.isBlank(oldPwd) || StrUtil.isBlank(newPwd)) {
            return JsonResult.failure("旧密码和新密码不能为空");
        }

        if (userId == null) {
            return JsonResult.failure("未获取到用户ID");
        }

        BaseUser entity = baseUserMapper.selectById(userId);
        if(entity == null) {
            return JsonResult.failure("未找到此用户");
        }

        if (!passwordEncoder.matches(oldPwd, entity.getPassword())) {
            return JsonResult.failure("原密码输入不正确");
        }
        entity.setPassword(passwordEncoder.encode(newPwd));
        if (baseUserMapper.updateById(entity) == 1) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.failure("修改失败");
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("{userId}")
    public JsonResult delete(@PathVariable("userId") Integer userId) {
        if (iacUserService.removeById(userId)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.failure("删除失败");
    }

//    @ApiOperation(value = "解绑微信")
//    @DeleteMapping("unbind")
//    public JsonResult unbind(Integer userId) {
//        BaseUser user = baseUserMapper.selectById(userId);
//        if (StrUtil.isNotBlank(user.getWxId())){
//            user.setWxId("");
//            baseUserMapper.updateById(user);
//            return JsonResult.ok("解绑成功");
//        }else {
//            return JsonResult.failure("未绑定微信");
//        }
//    }
}
