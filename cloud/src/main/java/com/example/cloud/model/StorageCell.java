package com.example.cloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageCell {

  private double batteryArrayV;
  private double batteryArrayI;
  private double batteryArraySoc;
  private double batteryArraySoh;
  private double batteryArrayAbleCharCap;
  private double batteryArrayAbleDiscCap;
  private double batteryArrayMaxV;
  private double batteryArrayMinV;
  private double batteryArrayMaxT;
  private double batteryArrayMinT;

}
