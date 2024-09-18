package cn.ruishan.iac.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("iac_strategy_schedule")
public class StrategySchedule {

  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "主键ID")
  private Integer id;

  @ApiModelProperty(value = "策略id")
  private Integer strategyId;

  @ApiModelProperty(value = "策略时间表内容")
  private String schedule;

}
