package cn.ruishan.iac.controller;

import cn.hutool.json.JSONUtil;
import cn.ruishan.common.web.JsonResult;
import cn.ruishan.iac.entity.StrategySchedule;
import cn.ruishan.iac.entity.Substation;
import cn.ruishan.iac.service.StrategyScheduleService;
import cn.ruishan.iac.service.SubstationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("iac/substation")
public class SubstationController {
    @Autowired
    private SubstationService substationService;
    @Autowired
    private StrategyScheduleService strategyScheduleService;

    @PostMapping("/addSub")
    public JsonResult addSub(Substation substation) {
        if (substationService.save(substation)){
            return JsonResult.ok("添加成功");
        }
        return JsonResult.failure("添加失败");
    }

    @DeleteMapping("/deleteById")
    public JsonResult delSubById(@RequestParam Integer id) {
        if (substationService.removeById(id)){
            return JsonResult.ok("删除成功");
        }
        return JsonResult.failure("删除失败");
    }

    @PostMapping("/updateOne")
    public JsonResult updateOne(Substation substation) {
        if(substationService.updateById(substation)){
            return JsonResult.ok("修改成功");
        }
        return JsonResult.failure("修改失败");
    }

    @GetMapping("/runningStatus")
    public JsonResult selectSubList(){
        return JsonResult.ok().setData(substationService.selectUserSubstation());
    }

    @PostMapping("/relUser")
    public JsonResult relUser(Integer substationId, String userIds){
        List<Integer> userIdList = JSONUtil.toList(JSONUtil.parseArray(userIds), Integer.class);
        if (substationService.setSubstationRelUser(substationId,userIdList)){
            return JsonResult.ok("关联成功");
        }
        return JsonResult.failure("关联成功");
    }
}
