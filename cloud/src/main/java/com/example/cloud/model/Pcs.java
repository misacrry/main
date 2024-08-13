package com.example.cloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pcs {

  private long id;
  private double totW;
  private double totVar;
  private double ppvPhsAb;
  private double ppvPhsBc;
  private double ppvPhsCa;
  private double phVPhsA;
  private double phVPhsB;
  private double phVPhsC;
  private double aPhsA;
  private double aPhsB;
  private double aPhsC;
  private double totPf;
  private long fre;
  private double phVDc;
  private double aDc;
  private double ableMaxTotCh;
  private double ableMaxToDi;
  private double charCapDay;
  private double discCapDay;
  private String acSwitchStatus;
  private String dcSwitchStatus;
  private String runStatus;
  private String charStatus;
  private double accumulatedChargeCap;
  private double accumulatedDischargeCap;

}
