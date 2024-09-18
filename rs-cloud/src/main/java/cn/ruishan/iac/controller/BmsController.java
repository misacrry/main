package cn.ruishan.iac.controller;

import cn.ruishan.common.web.JsonResult;
import cn.ruishan.iac.entity.Bms;
import cn.ruishan.iac.service.BmsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("iac/bms")
public class BmsController {

    @Resource
    private BmsService bmsService;

    @GetMapping("/selectBmsListByPointId")
    public JsonResult selectListByPointId(@RequestParam Integer pointId) {
        LambdaQueryWrapper<Bms> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Bms::getPointId, pointId);
        return JsonResult.ok().setData(bmsService.list(queryWrapper));
    }


    @DeleteMapping("/deleteById")
    public JsonResult deleteById(@RequestParam Integer id) {
        if (bmsService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.failure("删除失败");
    }

    @DeleteMapping("/deleteByPointId")
    public JsonResult deleteByPointId(@RequestParam Integer pointId) {
        LambdaQueryWrapper<Bms> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Bms::getPointId, pointId);
        if (bmsService.remove(queryWrapper)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.failure("删除失败");
    }

    @PostMapping("/addOne")
    @ResponseBody
    public JsonResult addOne(Bms bms) {
        if (bmsService.save(bms)) {
            return JsonResult.ok("新增成功");
        }
        return JsonResult.failure("新增失败");
    }

    @PostMapping("/updateOne")
    @ResponseBody
    public JsonResult updateOne(Bms bms) {
        if (bmsService.updateById(bms)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.failure("修改失败");
    }

}
