package cn.ruishan.iac.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("iac_bms")
public class Bms {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private Integer pointId;
  private Integer devId;
  private double voltage;
  private double current;
  private double soc;
  private double soh;
  private double ableCharCap;
  private double ableDiscCap;
  private double maxVol;
  private double minVol;
  private double maxTem;
  private double minTem;

  private Integer totWarning;
  private Integer totFault;
  private Integer prohibitCharge;
  private Integer prohibitDischarge;

}
