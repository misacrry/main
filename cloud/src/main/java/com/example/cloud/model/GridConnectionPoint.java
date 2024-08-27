package com.example.cloud.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridConnectionPoint {

  private int id;
  private int subId;
  private double phaseABLineV;
  private double phaseBCLineV;
  private double phaseCALineV;
  private double phaseAV;
  private double phaseBV;
  private double phaseCV;
  private double phaseAI;
  private double phaseBI;
  private double phaseCI;
  private double activeP;
  private double reactiveP;
  private double powerFactor;
  private int switchStatus;
  private double totalChargeElectricity;
  private double totalDischargeElectricity;
  private double totalChargePrice;
  private double totalDischargePrice;
  
}
