package cn.ruishan.common.base.entity;

import cn.ruishan.common.helper.LongTimeStampJsonDeserializer;
import cn.ruishan.common.helper.LongTimeStampJsonSerializer;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 基础 实体类
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
@Accessors(chain = true)
@Data
public abstract class BaseEntity<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "创建时间")
	@JsonDeserialize(using = LongTimeStampJsonDeserializer.class)
	@JsonSerialize(using = LongTimeStampJsonSerializer.class)
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	protected Long createTime;

	/**
	 * 更新日期
	 */
	@ApiModelProperty(value = "更新日期")
	@JsonDeserialize(using = LongTimeStampJsonDeserializer.class)
	@JsonSerialize(using = LongTimeStampJsonSerializer.class)
	@TableField(value = "update_time", fill = FieldFill.UPDATE)
	protected Long updateTime;

	/**
	 * 逻辑删除
	 * 0未删除，1删除
	 */
	@JsonIgnore
	@ApiModelProperty(value = "删除标识")
	@TableLogic
	protected Boolean deleted;


	@JsonIgnore
	@ApiModelProperty(value = "删除时间")
	@JsonDeserialize(using = LongTimeStampJsonDeserializer.class)
	@JsonSerialize(using = LongTimeStampJsonSerializer.class)
	@TableField(value = "delete_time", fill = FieldFill.UPDATE)
	protected Long deleteTime;
}
