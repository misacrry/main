package cn.ruishan.common.converter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc: 日期字符串转时间戳转换器
 * 用于表单提交日期转字符串
 * @author: longgang.lei
 * @time: 2021-04-23 13:53
 */
@Component
public class LongConverter implements Converter<String, Long> {

    private static final List<String> FORMARTS = new ArrayList<>(4);

    /**
     * 以下几种时间格式自动转成Date类型
     */
    static {
        FORMARTS.add("yyyy-MM-dd");
        FORMARTS.add("yyyy-MM-dd HH");
        FORMARTS.add("yyyy-MM-dd HH:mm");
        FORMARTS.add("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public Long convert(String source) {
        if (StrUtil.isEmpty(source)) {
            return null;
        }
        else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return DateUtil.parse(source, FORMARTS.get(0)).getTime();
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}$")) {
            return DateUtil.parse(source, FORMARTS.get(1)).getTime();
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return DateUtil.parse(source, FORMARTS.get(2)).getTime();
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return DateUtil.parse(source, FORMARTS.get(3)).getTime();
        } else {
            throw new IllegalArgumentException("Invalid dateStr value '" + source + "'");
        }
    }
}
