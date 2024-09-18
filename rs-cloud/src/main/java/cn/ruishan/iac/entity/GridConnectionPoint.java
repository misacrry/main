package cn.ruishan.iac.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("iac_grid_connection_point")
public class GridConnectionPoint {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private Integer substationId;
  private double phaseAbLineV;
  private double phaseBcLineV;
  private double phaseCaLineV;
  private double phaseAV;
  private double phaseBV;
  private double phaseCV;
  private double phaseAI;
  private double phaseBI;
  private double phaseCI;
  private double activeP;
  private double reactiveP;
  private double powerFactor;
  private Integer switchStatus;
  private double totalChargeElectricity;
  private double totalDischargeElectricity;
  private double totalChargePrice;
  private double totalDischargePrice;
  
}
