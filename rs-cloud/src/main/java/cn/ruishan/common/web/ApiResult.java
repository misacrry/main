package cn.ruishan.common.web;

import cn.ruishan.common.Constants;
import cn.ruishan.common.utils.JacksonUtil;

import java.util.HashMap;

/**
 * API返回结果对象
 * @author longgang.lei
 * @date 2019年9月5日
 */
public class ApiResult extends HashMap<String, Object> {


    private static final long serialVersionUID = 1L;

    /**
     * 状态码字段名称
     */
    private static final String CODE_NAME = "code";
    /**
     * 状态信息字段名称
     */
    private static final String MSG_NAME = "msg";
    /**
     * 数据字段名称
     */
    private static final String DATA_NAME = "data";
    /**
     * 默认成功码
     */
    private static final int DEFAULT_OK_CODE = Constants.RESULT_OK_CODE;
    /**
     * 默认失败码
     */
    private static final int DEFAULT_ERROR_CODE = Constants.RESULT_ERROR_CODE;
    /**
     * 默认成功msg
     */
    private static final String DEFAULT_OK_MSG = "操作成功";
    /**
     * 默认失败msg
     */
    private static final String DEFAULT_ERROR_MSG = "操作失败";

    private ApiResult() {
    }

    public static ApiResult build() {
        return new ApiResult();
    }

    /**
     * 返回成功
     */
    public static ApiResult ok() {
        return ok(null);
    }

    /**
     * 返回成功
     */
    public static ApiResult ok(String message) {
        return ok(DEFAULT_OK_CODE, message);
    }

    /**
     * 返回成功
     */
    public static ApiResult ok(int code, String message) {
        if (message == null) {
            message = DEFAULT_OK_MSG;
        }
        ApiResult jsonResult = new ApiResult();
        jsonResult.put(CODE_NAME, code);
        jsonResult.put(MSG_NAME, message);
        return jsonResult;
    }

    /**
     * 返回失败
     */
    public static ApiResult failure() {
        return failure(null);
    }

    /**
     * 返回失败
     */
    public static ApiResult failure(String message) {
        return failure(DEFAULT_ERROR_CODE, message);
    }

    /**
     * 返回失败
     */
    public static ApiResult failure(int code, String message) {
        if (message == null) {
            message = DEFAULT_ERROR_MSG;
        }
        return ok(code, message);
    }

    public ApiResult setCode(Integer code) {
        return put(CODE_NAME, code);
    }

    public ApiResult setMessage(String message) {
        return put(MSG_NAME, message);
    }

    public ApiResult setData(Object object) {
        return put(DATA_NAME, object);
    }

    public Integer getCode() {
        Object o = get(CODE_NAME);
        return o == null ? null : Integer.parseInt(o.toString());
    }

    public String getMessage() {
        Object o = get(MSG_NAME);
        return o == null ? null : o.toString();
    }

    public Object getData() {
        return get(DATA_NAME);
    }

    @Override
    public ApiResult put(String key, Object object) {
        if (key != null && object != null) {
            super.put(key, object);
        }
        return this;
    }

    @Override
    public String toString() {
        return JacksonUtil.obj2String(this);
    }
}
