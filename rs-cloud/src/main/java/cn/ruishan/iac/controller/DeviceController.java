package cn.ruishan.iac.controller;

import cn.ruishan.common.web.JsonResult;
import cn.ruishan.iac.entity.AncillaryControlData;
import cn.ruishan.iac.entity.Bms;
import cn.ruishan.iac.entity.DeviceStatus;
import cn.ruishan.iac.entity.Pcs;
import cn.ruishan.iac.service.AncillaryControlDataService;
import cn.ruishan.iac.service.BmsService;
import cn.ruishan.iac.service.PcsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/deviceStatus")
public class DeviceController {

    @Resource
    private PcsService pcsService;

    @Resource
    private BmsService bmsService;

    @Resource
    private AncillaryControlDataService ancillaryControlDataService;



    @GetMapping("/faultCounts")
    public JsonResult faultCounts() {
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setPcsAlarmCounts((int) pcsService.count(new LambdaQueryWrapper<Pcs>().eq(Pcs::getTotFault, 1)));
        deviceStatus.setPcsCounts((int) pcsService.count());
        deviceStatus.setBmsAlarmCounts((int) bmsService.count(new LambdaQueryWrapper<Bms>().eq(Bms::getTotFault, 1)));
        deviceStatus.setBmsCounts((int) bmsService.count());
        deviceStatus.setFireAlarmCounts((int) ancillaryControlDataService.count(
                new LambdaQueryWrapper<AncillaryControlData>().eq(AncillaryControlData::getFireControlTotFault, 1)));
        deviceStatus.setFireCounts((int) ancillaryControlDataService.count());
        return JsonResult.ok().setData(deviceStatus);
    }
}
