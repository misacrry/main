package cn.ruishan.iac.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("iac_pcs")
public class Pcs {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private Integer pointId;
  private Integer devId;
  private double totW;
  private double totVar;
  private double ppvPhsAb;
  private double ppvPhsBc;
  private double ppvPhsCa;
  private double phvPhsA;
  private double phvPhsB;
  private double phvPhsC;
  private double aPhsA;
  private double aPhsB;
  private double aPhsC;
  private double totPf;
  private long fre;
  @TableField("phv_dc")
  private double phVDc;
  private double aDc;
  private double ableMaxTotCh;
  private double ableMaxToDi;
  private double charCapDay;
  private double discCapDay;
  private double accumulatedChargeCap;
  private double accumulatedDischargeCap;
  private int acSwitchStatus;
  private int dcSwitchStatus;
  private int runStatus;
  private int totFault;
  private int totWarning;

}
