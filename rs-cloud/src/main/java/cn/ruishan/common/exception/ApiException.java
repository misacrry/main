package cn.ruishan.common.exception;

import cn.ruishan.common.web.ApiResult;

/**
 * Api接口异常
 * @author longgang.lei
 * @date 2019年9月5日
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 5450935008012318697L;

    private Integer code;

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        if (code == null) {
            code = 500;
        }
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (message == null) {
            message = "API接口异常";
        }
        return message;
    }

    public ApiResult transformApiResult() {
        return ApiResult.build().setCode(this.getCode()).setMessage(this.getMessage());
    }
}
