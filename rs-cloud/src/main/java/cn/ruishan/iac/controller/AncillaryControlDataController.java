package cn.ruishan.iac.controller;

import cn.ruishan.iac.service.AncillaryControlDataService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AncillaryControlDataController {

    @Resource
    public AncillaryControlDataService ancillaryControlDataService;


}
