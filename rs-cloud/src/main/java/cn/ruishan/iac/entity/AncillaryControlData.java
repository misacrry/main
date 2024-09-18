package cn.ruishan.iac.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "辅控数据表")
@TableName("iac_ancillary_control_data")
public class AncillaryControlData {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty(value = "环境温度")
  private double environmentT;

  @ApiModelProperty(value = "环境湿度")
  private double environmentH;

  @ApiModelProperty(value = "空调温度")
  private double airConditionerT;

  @ApiModelProperty(value = "消防报警")
  private Integer fireControlTotFault;

}
