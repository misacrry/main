package cn.ruishan.iac.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceStatus {
    private Integer bmsCounts;
    private Integer bmsAlarmCounts;
    private Integer pcsCounts;
    private Integer pcsAlarmCounts;
    private Integer airConditionCounts;
    private Integer airConditionAlarmCounts;
    private Integer fireCounts;
    private Integer fireAlarmCounts;
}
