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
@ApiModel(description = "策略表")
@TableName("iac_strategy")
public class Strategy {

  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "主键ID")
  private Integer id;

  @ApiModelProperty(value = "储能站id")
  private Integer substationId;

  @ApiModelProperty(value = "任务执行日期（格式：2024-07-01）")
  private String date;

}
