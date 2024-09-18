package cn.ruishan.iac.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataRequest {

    private String ver;
    private Long date;
    private Long devPresetStrategyVer;
    private Long devPlanCurveStrategyVer;
    private List<Devs> devs;

}
