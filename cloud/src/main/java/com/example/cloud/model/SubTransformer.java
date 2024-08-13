package com.example.cloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubTransformer {

  private long id;
  private double hPhaseAv;
  private double hPhaseBv;
  private double hPhaseCv;
  private double hPhaseAi;
  private double hPhaseBi;
  private double hPhaseCi;
  private double hActiveP;
  private double hReactiveP;
  private double hPowerIndex;
  private double lPhaseAv;
  private double lPhaseBv;
  private double lPhaseCv;
  private double lPhaseAi;
  private double lPhaseBi;
  private double lPhaseCi;
  private double lActiveP;
  private double lReactiveP;
  private double lPowerIndex;
  private String hSwitchStatus;
  private String lSwitchStatus;

}
