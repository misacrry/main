package cn.ruishan.iac.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色和资源关联表
 * </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@ApiModel(description = "角色和资源关联表")
@TableName("iac_rel_role_resource")
public class IacRelRoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableField(value = "role_id")
    private Integer roleId;

    @ApiModelProperty(value = "资源ID")
    @TableField(value = "resource_id")
    private Integer resourceId;
}
