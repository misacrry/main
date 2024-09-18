package cn.ruishan.iac.entity;

import cn.ruishan.sys.entity.IotCorp;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
@Data
@Accessors(chain = true)
@ApiModel(description="角色表")
@TableName("iac_role")
public class IacRole {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    @ApiModelProperty(value = "角色名称")
    @TableField(value = "`name`")
    private String name;

    @ApiModelProperty(value = "所属公司")
    @TableField(value = "corp_id")
    private Integer corpId;

    @ApiModelProperty(value="所属公司", hidden = true)
    @TableField(exist = false)
    private IotCorp corp;

    @ApiModelProperty(value = "描述")
    @TableField(value = "`desc`")
    private String desc;

    /**
     * 逻辑删除
     * 0未删除，1删除
     */
    @JsonIgnore
    @ApiModelProperty(value = "删除标识")
    @TableLogic
    protected Boolean deleted;
}
