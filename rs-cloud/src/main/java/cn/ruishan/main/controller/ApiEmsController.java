package cn.ruishan.main.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.ruishan.common.base.controller.BaseController;
import cn.ruishan.common.config.DataSourceConfig;
import cn.ruishan.common.security.LoginUtil;
import cn.ruishan.common.web.JsonResult;
import cn.ruishan.iac.entity.StrategyChargeDischarge;
import cn.ruishan.iac.entity.Substation;
import cn.ruishan.iac.service.StrategyChargeDischargeService;
import cn.ruishan.iac.service.StrategyService;
import cn.ruishan.iac.service.SubstationService;
import cn.ruishan.iac.vo.DataRequest;
import cn.ruishan.iac.vo.Devs;
import cn.ruishan.iac.vo.PresetStrategyData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.*;
import java.util.*;

// 接口
@Slf4j
@Api(value = "外部自定义Controller", tags = "外部自定义接口")
@RestController
@RequestMapping("api/ems")
public class ApiEmsController extends BaseController {

    @Resource
    private SubstationService substationService;

    @Resource
    private StrategyService strategyService;

    @Resource
    private StrategyChargeDischargeService strategyChargeDischargeService;

    /**
     * 装置鉴权
     */
    @PostMapping("/auth")
    public void subAuth(String serialNum, String uuid) {

    }

    @PostMapping("/data")
    public JsonResult uploadData(@RequestBody DataRequest dataRequest) {
        String sql = null;
        if (BeanUtil.isEmpty(LoginUtil.getLoginUser())){
            return JsonResult.failure("数据上送失败: 未登录");
        }

        Integer substationId = LoginUtil.getLoginUser().getSubstationId();
        if (substationId == 0) {
            return JsonResult.failure("数据上送失败: 无法查找到储能站");
        }

        JsonResult jsonResult = JsonResult.ok();

        List<Devs> devsList1 = dataRequest.getDevs();
        Long devPresetStrategyVer = dataRequest.getDevPresetStrategyVer();

        // 云平台预置策略版本
        Long cloudPresetStrategyVer = substationService.getById(substationId).getPresetStrategyVer();
        jsonResult.put("cloudPresetStrategyVer", cloudPresetStrategyVer);

        // 云平台计划曲线策略版本
        Long cloudPlanCurveStrategyVer = substationService.getById(substationId).getPlanCurveStrategyVer();
        jsonResult.put("cloudPlanCurveStrategyVer", cloudPlanCurveStrategyVer);

        if (cloudPresetStrategyVer > devPresetStrategyVer) {
            List<Map<String, Object>> strategyList = strategyChargeDischargeService.listMaps(new LambdaQueryWrapper<StrategyChargeDischarge>()
                    .eq(StrategyChargeDischarge::getSubstationId, substationId)
                    .select(StrategyChargeDischarge::getMonth,StrategyChargeDischarge::getStart,StrategyChargeDischarge::getEnd,StrategyChargeDischarge::getCharge,StrategyChargeDischarge::getValue,StrategyChargeDischarge::getEnable));
            jsonResult.put("presetStrategys",strategyList);
        }

        try (Connection connection = DriverManager.getConnection(DataSourceConfig.url, DataSourceConfig.username, DataSourceConfig.password)) {
            connection.setAutoCommit(false);

            for (Devs dev : devsList1) {
                sql = "UPDATE ";
                String tableName = "iac_" + dev.getType();
                sql += tableName + " SET ";
                int id = dev.getId();
                Map<String, Object> data = dev.getData();

                if (data != null) {
                    int totalEntries = data.size();
                    int currentIndex = 0;
                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                        currentIndex++;
                        sql += entry.getKey() + " = '" + entry.getValue() + "'";
                        if (currentIndex < totalEntries) {
                            sql += ", ";
                        } else {
                            sql += " ";
                        }
                    }
                }
                sql += "WHERE id = " + id;

                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                }
            }

            connection.commit();
            return jsonResult;

        } catch (SQLException e) {
            e.printStackTrace();
            return JsonResult.failure("数据上送失败: " + e).put("cloudPresetStrategyVer", cloudPresetStrategyVer);
        }
    }

    /**
     * 预置策略上送
     */
    @PostMapping("/strategy/preset")
    public JsonResult strategyPreset(@RequestBody PresetStrategyData presetStrategyData) {
        if (BeanUtil.isEmpty(LoginUtil.getLoginUser())){
            return JsonResult.failure("策略下发失败: 未登录");
        }

        Integer substationId = LoginUtil.getLoginUser().getSubstationId();
        if (substationId == 0) {
            return JsonResult.failure("策略下发失败: 无法查找到储能站");
        }

        Substation substation = substationService.getById(substationId);
        //比较本地预置设备版本跟云端版本
        if (presetStrategyData.getDevStrategyVer() > substation.getPresetStrategyVer()){
            List<StrategyChargeDischarge> strategys = presetStrategyData.getStrategys();
            List<StrategyChargeDischarge> strategyChargeDischargeList = strategyChargeDischargeService.list(new LambdaQueryWrapper<StrategyChargeDischarge>()
                    .eq(StrategyChargeDischarge::getSubstationId, substationId));
            if (strategys.size() != strategyChargeDischargeList.size()){
                return JsonResult.failure("策略下发失败: 下发策略数量不正确，云端数量：" + strategyChargeDischargeList.size());
            }
            //按顺序赋值
            for (int i = 0; i< strategys.size(); i++){
                StrategyChargeDischarge strategyChargeDischargeOld = strategyChargeDischargeList.get(i);
                StrategyChargeDischarge strategyChargeDischargeNew = strategys.get(i);
                strategyChargeDischargeOld.setStart(strategyChargeDischargeNew.getStart());
                strategyChargeDischargeOld.setEnd(strategyChargeDischargeNew.getEnd());
                strategyChargeDischargeOld.setEnable(strategyChargeDischargeNew.getEnable());
                strategyChargeDischargeOld.setValue(strategyChargeDischargeNew.getValue());
                strategyChargeDischargeService.updateById(strategyChargeDischargeOld);
            }
        }else {
            return JsonResult.failure("策略下发失败: 装置版本不高于云端版本");
        }
        return JsonResult.ok("策略下发成功");
    }
}
