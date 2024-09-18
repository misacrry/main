package cn.ruishan.common.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 树形Entity类
 * @author longgang.lei
 * @date 2019年9月10日
 * @param <T>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class TreeEntity<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @TableField(value = "`name`")
    protected String name;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级ID")
    @TableField(value = "parent_id")
    protected Integer parentId;

    /**
     * 父级名称
     */
    @ApiModelProperty(value = "父级名称", hidden = true)
    @TableField(exist = false)
    protected String parentName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序号")
    @TableField(value = "`sort`")
    protected Integer sort;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    protected String icon;
}
