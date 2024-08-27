package com.example.cloud.controller;

import com.example.cloud.model.Substation;
import com.example.cloud.service.SubstationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/substation")
public class SubstationController {
    @Resource
    private SubstationService substationService;

    @PostMapping("/addSub")
    @ResponseBody
    public int addSub(Substation substation) {
        return substationService.addSub(new Substation());
    }

    @DeleteMapping("/deleteById")
    public int delSubById(@RequestParam Integer id) {
        return substationService.delSubById(id);
    }

    @GetMapping("/runningStatus")
    public List<Substation> selectSubList(){
        return substationService.selectSubList();
    }
}
