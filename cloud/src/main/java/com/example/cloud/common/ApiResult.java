package com.example.cloud.common;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //状态码
    private int code;

    //返回数据
    private T data;

    //结果信息
    private String message;

    //时间字符串
    private String time;

    private ApiResult(){

    }

    public ApiResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    //定义成功的构造器
    private ApiResult(T data){
        this.code = ApiCode.SUCCESS.getCode();
        this.message = ApiCode.SUCCESS.getMsg();
        this.data = data;
        this.time = LocalDateTime.now().toString();

    }

    private ApiResult(ApiCode apiCode){
        this.code = apiCode.getCode();
        this.message = apiCode.getMsg();
        this.time = LocalDateTime.now().toString();
    }

    private ApiResult(int code,String msg){
        this.code = code;
        this.message = msg;
        this.time = LocalDateTime.now().toString();
    }

    private ApiResult(ApiCode apiCode,T data){
        this.code = apiCode.getCode();
        this.message = apiCode.getMsg();
        this.data = data;
        this.time = LocalDateTime.now().toString();
    }

    /**
     * 成功的时候调用
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ApiResult<T> success(T data){
        return new ApiResult(data);
    }

    /**
     * 根据状态返回结果
     * @param apiCode
     * @return
     * @param <T>
     */
    public static <T> ApiResult<T> build(ApiCode apiCode){
        return new ApiResult(apiCode);
    }

    /**
     * 根据code和msg返回结果
     * @param code
     * @param msg
     * @return
     * @param <T>
     */
    public static <T> ApiResult<T> build(int code,String msg){
        return new ApiResult(code,msg);
    }

    /**
     * 根据状态和数据返回结果
     * @param apiCode
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ApiResult<T> build(ApiCode apiCode,T data){
        return new ApiResult(apiCode,data);
    }


    /**
     * 返回异常结果
     * @param code
     * @param msg
     * @return
     * @param <T>
     */
    public static <T> ApiResult<T> error(int code,String msg){
        return new ApiResult(code,msg);
    }
}



