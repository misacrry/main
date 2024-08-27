package com.example.cloud.controller;

import com.example.cloud.model.GridConnectionPoint;
import com.example.cloud.service.GridConnectionPointService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/gcPoint")
public class GridConnectionPointController {

    @Resource
    private GridConnectionPointService gridConnectionPointService;

    @GetMapping("/selectList")
    public List<GridConnectionPoint> selectList() {
        return gridConnectionPointService.selectList();
    }

    @PostMapping("/addOne")
    @ResponseBody
    public int addOne(GridConnectionPoint gridConnectionPoint) {
        return gridConnectionPointService.addOne(gridConnectionPoint);
    }

    @DeleteMapping("/deleteBySubId")
    public int deleteBySubId(@RequestParam int subId) {
        return gridConnectionPointService.deleteBySubId(subId);
    }

    @DeleteMapping("/deleteById")
    public int deleteById(@RequestParam int id) {
        return gridConnectionPointService.deleteById(id);
    }




}
