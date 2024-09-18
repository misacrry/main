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
@TableName("iac_ems_auth")
public class EmsAuth {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private Integer substationId;
  private String serialNum;
  private String uuid;

}
