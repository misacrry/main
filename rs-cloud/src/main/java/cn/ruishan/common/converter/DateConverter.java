package cn.ruishan.common.converter;

import cn.hutool.core.date.DateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @desc: 日期字符串转日期转换器
 * @author: longgang.lei
 * @time: 2021-04-23 13:58
 */
@Component
public class DateConverter implements Converter<String, Date> {

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
    public Date convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return null;
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return DateUtil.parse(source, FORMARTS.get(0));
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}$")) {
            return DateUtil.parse(source, FORMARTS.get(1));
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return DateUtil.parse(source, FORMARTS.get(2));
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return DateUtil.parse(source, FORMARTS.get(3));
        } else {
            throw new IllegalArgumentException("Invalid date value '" + source + "'");
        }
    }
}
