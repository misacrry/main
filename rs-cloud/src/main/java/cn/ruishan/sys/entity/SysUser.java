package cn.ruishan.sys.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.ruishan.common.base.entity.UserEntity;
import cn.ruishan.main.entity.BaseUser;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author longgang.lei
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@ApiModel(description = "系统用户表")
@TableName("sys_user")
public class SysUser extends UserEntity<SysUser> {

    private static final long serialVersionUID = 242146703513492331L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value="手机号")
    private String phone;

    private String orgId;

    @Override
    public BaseUser buildBaseUser() {
        BaseUser baseUser = new BaseUser()
                .setLoginname(this.getLoginname())
                .setUsername(this.getUsername())
                .setPassword(this.getPassword())
                .setStatus(this.getStatus());
        if (this.userId != null) {
            baseUser.setUserId(userId);
        }
        return baseUser;
    }

    public SysUser buildUser(BaseUser baseUser) {
        this.setUserId(baseUser.getUserId());
        BeanUtil.copyProperties(baseUser, this);
        return this;
    }
}
