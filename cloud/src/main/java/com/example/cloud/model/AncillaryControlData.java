package com.example.cloud.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AncillaryControlData {

  private double environmentT;
  private double environmentH;
  private double airConditionerT;

}
