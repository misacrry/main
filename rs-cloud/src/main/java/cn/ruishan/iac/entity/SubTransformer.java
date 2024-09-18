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
@TableName("iac_sub_transformer")
public class SubTransformer {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private double hPhaseAV;
  private double hPhaseBV;
  private double hPhaseCV;
  private double hPhaseAI;
  private double hPhaseBI;
  private double hPhaseCI;
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
