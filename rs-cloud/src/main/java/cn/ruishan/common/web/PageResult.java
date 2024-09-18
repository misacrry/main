package cn.ruishan.common.web;

import cn.ruishan.common.Constants;
import cn.ruishan.common.utils.JacksonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果对象,这里以layui框架的table为标准
 * @author longgang.lei
 * @date 2019年9月5日
 * @param <T>
 */
@NoArgsConstructor
@Data
public class PageResult<T> {

    /**
     * 状态码, 0表示成功
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 总数量, bootstrapTable是total
     */
    private long count;

    /**
     * 当前数据, bootstrapTable是rows
     */
    private List<T> data;

    public PageResult(List<T> rows) {
        this(rows, rows.size());
    }

    public PageResult(List<T> rows, long total) {
        this.count = total;
        this.data = rows;
        this.code = Constants.RESULT_OK_CODE;
        this.msg = "";
    }

    @Override
    public String toString() {
        return JacksonUtil.obj2String(this);
    }
}
