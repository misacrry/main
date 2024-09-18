package cn.ruishan.main.controller;

import cn.hutool.core.collection.CollUtil;
import cn.ruishan.common.base.controller.BaseController;
import cn.ruishan.common.web.JsonResult;
import cn.ruishan.main.entity.LoginType;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * @AUTHOR RS-LLG
 * @DATE 2020-04-26 9:05
 **/
@RestController
public class MainController extends BaseController {

    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public void login(String username, String password, String loginType) {
        // 登录操作由JwtLoginFilter完成
    }

    @ApiOperation(value = "获取个人信息")
    @GetMapping("login/info")
    public JsonResult loginInfo() {
        // 不发送resource信息到前端，通过"login/resource"获取resource信息
        return JsonResult.ok().put("loginUser", getLoginUser().setResources(CollUtil.newArrayList()));
    }

    @PostMapping("")
    public ModelAndView index(String loginType, ModelAndView modelAndView) {

        String viewName = "index";

        if(modelAndView == null){
            modelAndView = new ModelAndView();
        }

        switch (loginType) {
            case LoginType.DEFAULT:
                viewName = "blank";
                break;
            case LoginType.SYS_ADMIN:
                viewName = "index";
                break;
            case LoginType.IOT_CORP:
                viewName = "index";
                break;
            case LoginType.IAC_USR_WEB:
                viewName = "index";
                break;
            default:
                break;
        }

        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @GetMapping("")
    public ModelAndView index() {
        return index(LoginType.DEFAULT, null);
    }
}
