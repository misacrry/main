package cn.ruishan.iac.controller;

import cn.ruishan.common.web.JsonResult;
import cn.ruishan.iac.entity.Pcs;
import cn.ruishan.iac.service.PcsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("iac/pcs")
public class PcsController {

    @Resource
    PcsService pcsService;

    @GetMapping("/selectList")
    public JsonResult selectList() {
        return JsonResult.ok().setData(pcsService.list());
    }

    @GetMapping("/selectPcsListByPointId")
    public JsonResult selectPcsListByPointId(@RequestParam Integer pointId) {
        LambdaQueryWrapper<Pcs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Pcs::getPointId, pointId);
        return JsonResult.ok().setData(pcsService.list(queryWrapper));
    }


    @DeleteMapping("/deleteById")
    public JsonResult deleteById(@RequestParam Integer id) {
        if (pcsService.removeById(id)) {
            return JsonResult.ok("刪除成功");
        }
        return JsonResult.failure("刪除失败");
    }

    @DeleteMapping("/deleteByPointId")
    public JsonResult deleteBySubId(@RequestParam Integer pointId) {
        LambdaQueryWrapper<Pcs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Pcs::getPointId, pointId);
        if (pcsService.remove(queryWrapper)) {
            return JsonResult.ok("刪除成功");
        }
        return JsonResult.failure("刪除失败");
    }

    @PostMapping("/addOne")
    public JsonResult addOne(Pcs pcs) {
        if (pcsService.save(pcs)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.failure("添加失败");
    }

    @PostMapping("/updateOne")
    public JsonResult updateOne(Pcs pcs) {
        if (pcsService.updateById(pcs)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.failure("修改失败");
    }

}
