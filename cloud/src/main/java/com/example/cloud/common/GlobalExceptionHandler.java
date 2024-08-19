package com.example.cloud.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //自定义异常
    @ExceptionHandler(BusinessException.class)
    public ApiResult systemExceptionHandler(BusinessException e) {
        log.error("BusinessException全局异常：{}",e);
        return ApiResult.error(e.getCode(), e.getMsg());
    }

    //系统异常
    @ExceptionHandler(Exception.class)
    public ApiResult exceptionHandler(Exception e) {
        System.out.println(e);
        log.error("Exception全局异常：{}",e);
        if (e instanceof BadCredentialsException) {
            return ApiResult.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        }
        return ApiResult.error(ApiCode.SYSTEM_ERROR.getCode(), e.getMessage());
    }

//    /**
//     * 全局捕获security的权限不足异常
//     */
//    @ExceptionHandler(value = AccessDeniedException.class)
//    public ApiResult accessDeniedException(AccessDeniedException e) {
//        log.error("权限不足异常！原因是：[{}]", e.getMessage());
//        return ApiResult.error(HttpStatus.FORBIDDEN.value(), "禁止访问");
//    }
//
//    /**
//     * 全局捕获security的认证失败异常
//     */
//    @ExceptionHandler(value = AuthenticationException.class)
//    public ApiResult authenticationException(AuthenticationException e) {
//        log.error("用户认证失败异常！原因是：[{}]", e.getMessage());
////        throw e;
//        return ApiResult.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
//    }

}
