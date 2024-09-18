package cn.ruishan.iac.entity;


import cn.hutool.core.date.DateTime;
import cn.ruishan.common.helper.LongTimeStampJsonSerializer;
import cn.ruishan.common.helper.LongTimeStampJsonSerializer1;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("iac_substation")
public class Substation {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private double totW;
  private double totVar;
  private double charCapDay;
  private double discCapDay;
  private double accumulatedChargeCap;
  private double accumulatedDischargeCap;
  private double accumulatedChargeTime;
  private double accumulatedDischargeTime;
  private double ableMaxTotCh;
  private double ableMaxTotDi;
  @JsonSerialize(using = LongTimeStampJsonSerializer.class)
  private Long ableMaxTotChTime;
  @JsonSerialize(using = LongTimeStampJsonSerializer.class)
  private Long ableMaxTotDiTime;
  private long dailyCgCnt;
  private long monthlyCgCnt;
  private int stationStatus;
  private int soc;
  private double accumulatedRunningTime;
  private double ableCharCap;
  private double ableDiscCap;
  private double overallEfficiency;
  private double runningFactor;
  private double utilRateInd;
  private double storageScrapRate;
  private double utilFactor;
  private double accumulatedRev;
  private double todayRev;
  private double yesterdayRev;
  private double monthlyRev;
  private double yearlyRev;
  private long equivalentChargeDischargeTime;
  private int latitude;
  private int longitude;
  @JsonSerialize(using = LongTimeStampJsonSerializer.class)
  private Long operationTime;
  private String description;
  private Long presetStrategyVer;
  private Long planCurveStrategyVer;
  private Integer mode;
}
