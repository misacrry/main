package cn.ruishan.common.websocket;

import cn.ruishan.common.utils.JacksonUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @desc: 消息
 * @author: longgang.lei
 * @create: 2021-12-23 11:50
 **/
@Accessors(chain = true)
@Data
public class WebSocketMessage implements Serializable {

    private Integer type;
    private String content;

    @Override
    public String toString() {
        return JacksonUtil.obj2String(this);
    }
}
