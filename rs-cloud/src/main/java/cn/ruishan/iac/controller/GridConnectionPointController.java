package cn.ruishan.iac.controller;

import cn.ruishan.common.web.JsonResult;
import cn.ruishan.iac.entity.GridConnectionPoint;
import cn.ruishan.iac.service.GridConnectionPointService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("iac/gcPoint")
public class GridConnectionPointController {

    @Resource
    private GridConnectionPointService gridConnectionPointService;

    @GetMapping("/selectList")
    public JsonResult selectList() {
        return JsonResult.ok().setData(gridConnectionPointService.list());
    }

    @GetMapping("/selectListBySubId")
    public JsonResult selectListBySubId(@RequestParam Integer subId) {
        LambdaQueryWrapper<GridConnectionPoint> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GridConnectionPoint::getSubstationId, subId);
        return JsonResult.ok().setData(gridConnectionPointService.list(queryWrapper));
    }

    @PostMapping("/addOne")
    @ResponseBody
    public JsonResult addOne(@RequestParam Integer subId) {
        GridConnectionPoint gridConnectionPoint = new GridConnectionPoint();
        gridConnectionPoint.setSubstationId(subId);
        if (gridConnectionPointService.save(gridConnectionPoint)) {
            return JsonResult.ok("新增成功");
        }
        return JsonResult.failure("新增失败");
    }

    @DeleteMapping("/deleteBySubId")
    public JsonResult deleteBySubId(@RequestParam int subId) {
        LambdaQueryWrapper<GridConnectionPoint> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GridConnectionPoint::getSubstationId, subId);
        if (gridConnectionPointService.remove(queryWrapper)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.failure("删除失败");
    }

    @DeleteMapping("/deleteById")
    public JsonResult deleteById(@RequestParam int id) {
        if (gridConnectionPointService.removeById(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.failure("删除失败");
    }


}
