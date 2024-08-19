package com.example.cloud.common;

import lombok.Data;

@Data

public class BusinessException extends RuntimeException{

    private int code;

    private String msg;

    public BusinessException(ApiCode apiCode) {
        super(apiCode.getMsg());
        this.code = apiCode.getCode();
        this.msg = apiCode.getMsg();
    }

    public BusinessException(String msg) {

        this.msg = msg;
    }
}
