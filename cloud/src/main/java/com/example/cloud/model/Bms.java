package com.example.cloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bms {

  private Integer id;
  private Integer pointId;
  private Integer equipmentId;
  private long bmsTotWarning;
  private long bmsTotFault;
  private long batteryStackVoltLimit;
  private long batteryStackCurLimit;

  private int totalCounts;
  private int faultCounts;
}
