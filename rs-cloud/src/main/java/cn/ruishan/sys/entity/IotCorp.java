package cn.ruishan.sys.entity;

import cn.ruishan.common.base.entity.DataEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 企业表
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(description="企业表")
@TableName("iot_corp")
public class IotCorp extends DataEntity<IotCorp> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业ID")
    @TableId(value = "corp_id", type = IdType.AUTO)
    private Integer corpId;

    @ApiModelProperty(value = "企业名称")
    @TableField(value = "`name`")
    private String name;

    @ApiModelProperty(value = "管理员登录名称")
    @TableField(value = "login_name")
    private String loginName;

    @ApiModelProperty(value = "管理员登陆密码")
    private String password;

    /**
     * 状态(0正常，1冻结)
     */
    @ApiModelProperty(value = "状态")
    private Boolean status;
}
