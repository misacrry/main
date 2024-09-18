package cn.ruishan.iac.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("iac_battery_cell")
public class BatteryCell {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private Integer clusterId;
  private double vol;
  private double current;

}
