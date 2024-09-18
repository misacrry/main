package cn.ruishan.common.base.entity;

import cn.ruishan.common.helper.LongTimeStampJsonDeserializer;
import cn.ruishan.common.helper.LongTimeStampJsonSerializer;
import cn.ruishan.main.entity.BaseUser;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 基础 用户虚拟类
 * 用于统一用户管理
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
@Accessors(chain = true)
@Data
public abstract class UserEntity<T> implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  登陆账户
	 */
	@TableField(exist = false)
	protected String loginname;

	/**
	 *  登陆名
	 */
	@TableField(exist = false)
	protected String username;

	/**
	 *  密码
	 */
	@TableField(exist = false)
	protected String password;

	/**
	 *  状态
	 */
	@TableField(exist = false)
	protected Boolean status;

	/**
	 *  关联角色ID列表
	 */
	@TableField(exist = false)
	protected List<Integer> roleIds;

	/**
	 *  创建者
	 */
	@TableField(exist = false)
	protected Integer createId;

	/**
	 *  创建时间
	 */
	@JsonDeserialize(using = LongTimeStampJsonDeserializer.class)
	@JsonSerialize(using = LongTimeStampJsonSerializer.class)
	@TableField(exist = false)
	protected Long createTime;

	/**
	 * 更新者
	 */
	@TableField(exist = false)
	protected Integer updateId;

	/**
	 *  更新时间
	 */
	@JsonDeserialize(using = LongTimeStampJsonDeserializer.class)
	@JsonSerialize(using = LongTimeStampJsonSerializer.class)
	@TableField(exist = false)
	protected Long updateTime;

	/**
	 * 逻辑删除
	 * 0未删除，1删除
	 */
	@TableField(exist = false)
	protected Boolean deleted;

	/**
	 * 删除者
	 */
	@TableField(exist = false)
	protected Integer deleteId;

	/**
	 *  删除时间
	 */
	@JsonDeserialize(using = LongTimeStampJsonDeserializer.class)
	@JsonSerialize(using = LongTimeStampJsonSerializer.class)
	@TableField(exist = false)
	protected Long deleteTime;

	public abstract BaseUser buildBaseUser();
}
