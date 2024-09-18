package cn.ruishan.iac.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色和用户关联表
 * </p>
 */
@Data
@Accessors(chain = true)
@ApiModel(description="角色和用户关联表")
@TableName("iac_rel_role_user")
public class IacRelRoleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableField(value = "role_id")
    private Integer roleId;

    @ApiModelProperty(value = "用户ID")
    @TableField(value = "user_id")
    private Integer userId;
}
