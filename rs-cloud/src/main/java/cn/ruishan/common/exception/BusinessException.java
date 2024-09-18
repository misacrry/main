package cn.ruishan.common.exception;

import cn.ruishan.common.web.JsonResult;

/**
 * 业务异常
 * @author longgang.lei
 * @date 2019年9月5日
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 5450935008012318697L;

    private Integer code;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code, String message) {
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
            message = "业务处理异常";
        }
        return message;
    }

    public JsonResult transformJsonResult() {
        return JsonResult.build().setCode(this.getCode()).setMessage(this.getMessage());
    }
}
