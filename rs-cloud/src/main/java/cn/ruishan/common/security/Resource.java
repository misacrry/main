package cn.ruishan.common.security;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @desc: 资源
 * @author: longgang.lei
 * @create: 2021-04-09 12:53
 **/
@Accessors(chain = true)
@Data
public class Resource implements Serializable {

    private Integer resourceId;

    protected Integer parentId;

    protected String parentName;

    protected String name;

    protected String url;

    private String authority;

    private Integer type;

    protected Integer sort;

    protected String icon;
}
