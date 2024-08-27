package com.example.cloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireControl {

  private int id;
  private int fireControlAlarm;
  private int gasAlarm;
  private int fireControlTotFault;

}
