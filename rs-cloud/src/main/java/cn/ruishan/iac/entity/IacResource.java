package cn.ruishan.iac.entity;

import cn.ruishan.common.base.entity.TreeEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@ApiModel(description = "工商业资源表")
@TableName("iac_resource")
public class IacResource extends TreeEntity<IacResource> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "resource_id", type = IdType.AUTO)
    private Integer resourceId;

    /**
     * 路径
     */
    @ApiModelProperty(value = "资源路径")
    protected String url;

    /**
     * 对应权限
     */
    @ApiModelProperty(value = "资源权限")
    private String authority;

    /**
     * 资源类型
     *
     * 0：菜单 1：权限/按钮
     */
    @ApiModelProperty(value = "资源类型")
    private Integer type;
}
