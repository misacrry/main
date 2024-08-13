package com.example.cloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatteryCell {

  private long volLimit;
  private long volRangeLimit;
  private long tmpLimit;
  private long tmpRangeLimit;

}
