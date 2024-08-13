package com.sample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatteryCluster {

  private double batteryClusterV;
  private double batteryClusterI;
  private double batteryClusterSoc;
  private double batteryClusterSoh;
  private double batteryClusterAbleEnergy;
  private double batteryClusterAbleDsEnergy;
  private double cellMaxV;
  private double cellMinV;
  private double cellMaxT;
  private double cellMinT;
  private double cellMaxVolPos;
  private double cellMinVolPos;
  private double cellMaxTmpPos;
  private double cellMiniTmpPos;
  private double dcSwitchState;
  private long actorBatVoltLimit;
  private long actorBatCurLimit;
  private long resInsLimit;
  private long intCirLimit;
  private long actorBatLoopAbnl;
  private long actorModlVoltLimit;

}

