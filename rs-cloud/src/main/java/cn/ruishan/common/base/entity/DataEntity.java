package cn.ruishan.common.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 数据Entity类
 * @author longgang.lei
 * @date 2019年9月10日
 * @param <T>
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class DataEntity<T> extends BaseEntity<T> {

    private static final long serialVersionUID = 1L;

    /**
     *  创建者
     */
    @ApiModelProperty(value = "创建者ID")
    @TableField(value = "create_id", fill = FieldFill.INSERT)
    protected Integer createId;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者ID")
    @TableField(value = "update_id", fill = FieldFill.UPDATE)
    protected Integer updateId;

    /**
     * 删除者
     */
    @JsonIgnore
    @ApiModelProperty(value = "删除者ID")
    @TableField(value = "delete_id", fill = FieldFill.UPDATE)
    protected Integer deleteId;
}
