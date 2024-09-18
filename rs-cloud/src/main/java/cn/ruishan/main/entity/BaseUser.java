package cn.ruishan.main.entity;

import cn.ruishan.common.base.entity.DataEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @desc: 基础用户
 * @author: longgang.lei
 * @create: 2021-04-06 08:48
 **/
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@ApiModel(description = "基础用户表")
@TableName("base_user")
public class BaseUser extends DataEntity<BaseUser> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value="登陆名/账号", name="loginname", required = true)
    private String loginname;

    @ApiModelProperty(value="用户名", name="username", required = true)
    private String username;

    @JsonIgnore
    @ApiModelProperty(value="密码", name="password", required = true)
    private String password;

    @ApiModelProperty(value="状态(0正常，1冻结)")
    private Boolean status;

    @ApiModelProperty(value="微信id")
    private String wxId;


}
