package cn.ruishan.common.annotation;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @desc: 分页查询接口统一参数
 * @author: longgang.lei
 * @time: 2021-04-05 13:23
 */
@ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "第几页", required = true, dataTypeClass=Integer.class, paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataTypeClass=Integer.class, paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataTypeClass=String.class, paramType = "query"),
        @ApiImplicitParam(name = "order", value = "排序方式", dataTypeClass=String.class, paramType = "query")
})
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiPageParam {
}
