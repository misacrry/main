package com.example.cloud.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridConnectionPoint {

  private long id;
  private double phaseAbLineV;
  private double phaseBcLineV;
  private double phaseCaLineV;
  private double phaseAv;
  private double phaseBv;
  private double phaseCv;
  private double phaseAi;
  private double phaseBi;
  private double phaseCi;
  private double activeP;
  private double reactiveP;
  private double powerFactor;
  private String switchStatus;
  private double totalChargeElectricity;
  private double totalDischargeElectricity;
  private double totalChargePrice;
  private double totalDischargePrice;

}
