package com.example.cloud.controller;

import com.example.cloud.model.Bms;
import com.example.cloud.service.BmsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bms")
public class BmsController {

    @Resource
    private BmsService bmsService;

    @GetMapping("/faultCounts")
    public Bms faultCounts() {
        return bmsService.selectFaultCounts();
    }

    @DeleteMapping("/deleteById")
    public int deleteById(@RequestParam Integer id) {
        System.out.println(id);
        return bmsService.deleteById(id);
    }

    @DeleteMapping("/deleteBySubId")
    public int deleteBySubId(@RequestParam Integer id) {
        return bmsService.deleteByPointId(id);
    }

    @PostMapping("/addOne")
    @ResponseBody
    public int addOne(Bms bms) {
        return bmsService.addOne(bms);
    }
//
//    @PostMapping("/addOneBySubId")
//    @ResponseBody
//    public int addOneBySubId(Bms bms) {
//        Bms bms1 = new Bms();
//        bms1.setSubId(bms.getId());
//        return bmsService.addOne(bms1);
//    }

}
