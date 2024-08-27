package com.example.cloud.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Substation {

  private Integer id;
  private double totW;
  private double totVar;
  private double charCapDay;
  private double discCapDay;
  private double accumulatedChargeCap;
  private double accumulatedDischargeCap;
  private double accumulatedChargeTime;
  private double accumulatedDischargeTime;
  private long dailyCgCnt;
  private long monthlyCgCnt;
  private String stationStatus;
  private String soc;
  private double accumulatedRunningTime;
  private double ableCharCap;
  private double ableDiscCap;
  private double ableEnergy;
  private double ableDsEnergy;
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
  private DateTime operationTime;
  private String description;


}
