package cn.ruishan.iac.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "策略详细表")
@TableName("iac_strategy_detail")
public class StrategyDetail {

  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "主键ID")
  private Integer id;

  @ApiModelProperty(value = "策略id")
  private Integer strategyId;

  @ApiModelProperty(value = "开始时间（格式：08:00:00）")
  private String start;

  @ApiModelProperty(value = "结束时间（格式：12:00:00）")
  private String end;

  @ApiModelProperty(value = "是否充电模式")
  private Bool change;

  @ApiModelProperty(value = "充放电总功率值")
  private double value;
}
