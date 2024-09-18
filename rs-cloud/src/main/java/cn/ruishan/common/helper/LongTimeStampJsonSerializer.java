package cn.ruishan.common.helper;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Long类型字段序列化
 * 将数据库取出的Long类型时间戳转化为时间字符串格式，展示给前端
 * 在字段上加注解 @JsonSerialize(using = LongTimeStampJsonSerializer.class)
 */
@Slf4j
public class LongTimeStampJsonSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        try {
            String text = (value == null ? null : DateUtil.date(value).toString());
            if (text != null) {
                jsonGenerator.writeString(text);
            }
        } catch (Exception e) {
            log.error("Long类型时间戳转为时间字符串: {}", e);
        }
    }
}
