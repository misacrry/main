package cn.ruishan.iac.vo;

import cn.ruishan.iac.entity.StrategyChargeDischarge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresetStrategyData {
        private Long devStrategyVer;
        private List<StrategyChargeDischarge> strategys;

}
