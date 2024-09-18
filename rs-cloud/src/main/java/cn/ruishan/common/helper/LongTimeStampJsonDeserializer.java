package cn.ruishan.common.helper;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

/**
 * Long类型字段反序列化
 * 将前端传入的时间格式字符串转为Long时间戳
 * 在字段上加注解 @JsonDeserialize(using = LongTimeStampJsonDeserializer.class)
 */
@Slf4j
public class LongTimeStampJsonDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        String value = null;
        try {
            value = jsonParser.getText();
            return value == null ? null : DateUtil.parse(value, DatePattern.NORM_DATETIME_PATTERN).getTime();
        } catch (Exception e) {
            log.error("时间字符串转为Long时间戳错误: {}", e);
            return null;
        }
    }
}
