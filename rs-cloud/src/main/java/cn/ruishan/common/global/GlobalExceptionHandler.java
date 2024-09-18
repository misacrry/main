package cn.ruishan.common.global;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.ruishan.common.exception.ApiException;
import cn.ruishan.common.exception.BusinessException;
import cn.ruishan.common.security.LoginUtil;
import cn.ruishan.common.utils.ResponseUtil;
import cn.ruishan.common.utils.UserAgentGetter;
import cn.ruishan.common.web.ApiResult;
import cn.ruishan.common.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * 全局异常处理
 * 利用这个注解既可以返回json数据的类型，也可以跳转至自定义的页面
 */
@ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Log log = Log.get(GlobalExceptionHandler.class);

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @ExceptionHandler(value = ApiException.class)
    public ApiResult apiExHandle(ApiException e, HttpServletResponse response){
        cross(response);  // 支持跨域
        response.setStatus(e.getCode());
        log.error("API请求异常：", e);
        return e.transformApiResult();
    }

    @ExceptionHandler(value = BusinessException.class)
    public JsonResult businessExHandle(BusinessException e, HttpServletResponse response){
        cross(response);  // 支持跨域
        log.error("业务请求异常：", e);
        return e.transformJsonResult();
    }

    @ExceptionHandler(value = BadSqlGrammarException.class)
    public void sqlExHandle(BadSqlGrammarException e, HttpServletResponse response) {
        cross(response);  // 支持跨域
        log.error("sql异常：", e);
        ResponseUtil.write(response, JsonResult.failure("SQL异常"));
    }

    @ExceptionHandler(value = SQLException.class)
    public void sqlExHandle(SQLException e, HttpServletResponse response) {
        cross(response);  // 支持跨域
        log.error("sql异常：", e);
        ResponseUtil.write(response, JsonResult.failure("SQL异常"));
    }

    @ExceptionHandler()
    public void exHandle(Exception e, HttpServletResponse response) {
        cross(response);  // 支持跨域
        log.error("默认异常：", e);
        ResponseUtil.write(response, JsonResult.failure(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public void runtimeExHandle(Exception e, HttpServletResponse response) {
        cross(response);  // 支持跨域
        log.error("运行时异常：", e);
        //this.saveLog(e, "运行时异常");
        ResponseUtil.write(response, JsonResult.failure(e.getMessage()));
    }

    private void cross(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");
    }
}
