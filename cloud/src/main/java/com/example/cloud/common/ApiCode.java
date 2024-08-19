package com.example.cloud.common;

public enum ApiCode {

    SUCCESS(200, "成功"),

    SYSTEM_ERROR(500, "操作失败"),

    NOT_FOUND(404,"未找到该资源");

    private final int code;
    private final String msg;

    ApiCode(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
