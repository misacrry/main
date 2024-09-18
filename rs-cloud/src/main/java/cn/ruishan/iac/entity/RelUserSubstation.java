package cn.ruishan.iac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("iac_rel_user_substation")
public class RelUserSubstation {

  private Integer userId;

  private Integer substationId;

}
