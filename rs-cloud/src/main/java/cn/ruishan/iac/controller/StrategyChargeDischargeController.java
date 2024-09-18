package cn.ruishan.iac.controller;

import cn.ruishan.common.web.JsonResult;
import cn.ruishan.iac.entity.StrategyChargeDischarge;
import cn.ruishan.iac.service.StrategyChargeDischargeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtflys.forest.annotation.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("iac/strategyChargeDisCharge")
public class StrategyChargeDischargeController {
    @Autowired
    private StrategyChargeDischargeService strategyChargeDischargeService;

    @GetMapping("/listByMonth")
    public JsonResult list(@RequestParam Integer substationId, @RequestParam Integer month){
        List<StrategyChargeDischarge> strategyChargeDischargeList = strategyChargeDischargeService.list(new LambdaQueryWrapper<StrategyChargeDischarge>()
                .eq(StrategyChargeDischarge::getMonth,month)
                .eq(StrategyChargeDischarge::getSubstationId,substationId));
        return JsonResult.ok().setData(strategyChargeDischargeList);
    }

    @PostMapping("/update")
    public JsonResult update(@RequestBody List<StrategyChargeDischarge> strategyChargeDischarges) {
        if(strategyChargeDischargeService.updateMonth(strategyChargeDischarges)){
            return JsonResult.ok("修改成功");
        }
        return JsonResult.failure("修改失败");
    }
}
