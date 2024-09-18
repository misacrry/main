package cn.ruishan.iac.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("iac_battery_cluster")
public class BatteryCluster {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private double voltage;
  private double current;
  private double soc;
  private double soh;
  private double ableChCap;
  private double ableDsCap;
  private double cellMaxV;
  private double cellMinV;
  private double cellMaxT;
  private double cellMinT;
  private double cellMaxVolPos;
  private double cellMinVolPos;
  private double cellMaxTmpPos;
  private double cellMinTmpPos;
  private double dcSwitchState;
  private Integer totWarning;
  private Integer totFault;
  private Integer prohibitCharge;
  private Integer prohibitDischarge;

}
