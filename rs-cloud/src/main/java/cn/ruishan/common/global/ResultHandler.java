package cn.ruishan.common.global;

import cn.ruishan.common.web.ApiResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

@RestControllerAdvice
public class ResultHandler implements ResponseBodyAdvice {

    private static final Class[] METHED_ANNOS = {
            RequestMapping.class,
            GetMapping.class,
            PostMapping.class,
            DeleteMapping.class,
            PutMapping.class};

    /**
     * 对所有METHED_ANNOS中的RestController方法
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        AnnotatedElement element = returnType.getAnnotatedElement();
        return Arrays.stream(METHED_ANNOS)
                .anyMatch(anno -> anno.isAnnotation() && element.isAnnotationPresent(anno));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if(body instanceof ApiResult){
            ApiResult apiResult = (ApiResult) body;
            response.setStatusCode(HttpStatus.valueOf(apiResult.getCode()));
            return apiResult;
        }

        // 这里可对返回结果进行处理
        return body;
    }
}
