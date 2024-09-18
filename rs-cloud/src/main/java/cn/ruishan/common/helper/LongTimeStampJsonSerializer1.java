package cn.ruishan.common.helper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @desc: Long类型时间戳序列化转为时间字符串
 * 用于前台展示时间格式
 * @author: longgang.lei
 * @time: 2021-04-23 14:02
 */
public class LongTimeStampJsonSerializer1 extends JsonSerializer<Long> {

    private static final Log log = Log.get(LongTimeStampJsonSerializer1.class);

    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        try {
            String text = (value == null ? null : DateUtil.format(DateUtil.date(value),"yyyy-MM-dd"));
            if (text != null) {
                jsonGenerator.writeString(text);
            }
        } catch (Exception e) {
            log.error("Long类型时间戳转为时间字符串: {}", e);
        }
    }
}
