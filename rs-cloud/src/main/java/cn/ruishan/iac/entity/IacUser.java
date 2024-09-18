package cn.ruishan.iac.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.ruishan.common.base.entity.UserEntity;
import cn.ruishan.common.helper.LongTimeStampJsonDeserializer;
import cn.ruishan.common.helper.LongTimeStampJsonSerializer;
import cn.ruishan.main.entity.BaseUser;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(description = "工商业用户表")
@TableName("iac_user")
public class IacUser extends UserEntity<IacUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id")
    private Integer userId;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "公司")
    private Integer corpId;

    @TableField(exist = false)
    private String corpName;

    @ApiModelProperty(value="关联角色", hidden = true)
    @TableField(exist = false)
    private List<Integer> roleIds;

    @ApiModelProperty(value="关联角色", hidden = true)
    @TableField(exist = false)
    private List<IacRole> roles;

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

    public IacUser buildUser(BaseUser baseUser) {
        this.setUserId(baseUser.getUserId());
        BeanUtil.copyProperties(baseUser, this);
        return this;
    }

    /**
     * @desc: 获取需要的信息
     * @author: longgang.lei
     * @time: 2021-03-29 11:19
     */
    public Map<String, Object> toEasyMap(Map<String, Object> easyMap) {
        easyMap.put("userId", this.getUserId());
        easyMap.put("userName", this.getUsername());
        easyMap.put("loginName", this.getLoginname());
        return easyMap;
    }
}
