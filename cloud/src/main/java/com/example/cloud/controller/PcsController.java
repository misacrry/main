package com.example.cloud.controller;

import com.example.cloud.model.Pcs;
import com.example.cloud.service.PcsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/pcs")
public class PcsController {

    @Resource
    PcsService pcsService;

    @GetMapping("/selectList")
    public List<Pcs> selectList() {
        return pcsService.selectList();
    }

    @GetMapping("/faultCounts")
    public Pcs faultCounts() {
        return pcsService.faultCounts();
    }

    @DeleteMapping("/deleteById")
    public int deleteById(@RequestParam Integer id) {
        return pcsService.deleteById(id);
    }

    @DeleteMapping("/deleteBySubId")
    public int deleteBySubId(@RequestParam Integer id) {
        return pcsService.deleteByPointId(id);
    }

    @PostMapping("/addOne")
    @ResponseBody
    public int addOne(Pcs pcs) {
        return pcsService.addOne(pcs);
    }

//    @PostMapping("/addOneBySubId")
//    @ResponseBody
//    public int addOneBySubId(Pcs pcs) {
//        Pcs pcs1 = new Pcs();
//        pcs1.setSubId(pcs.getId());
//        return pcsService.addOne(pcs1);
//    }
}
