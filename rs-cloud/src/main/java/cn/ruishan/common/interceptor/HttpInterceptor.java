package cn.ruishan.common.interceptor;

import cn.ruishan.common.utils.service.HttpFailedCacheService;
import com.dtflys.forest.converter.ForestEncoder;
import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.Interceptor;
import com.dtflys.forest.reflection.ForestMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HttpInterceptor<T> implements Interceptor<T> {

    @Value("${forest.error-count}")
    private Integer errorCount = 5;

    @Autowired
    private HttpFailedCacheService cacheService;

    /**
     * 该方法在被调用时，并在beforeExecute前被调用
     * @Param request Forest请求对象
     * @Param args 方法被调用时传入的参数数组
     */
    @Override
    public void onInvokeMethod(ForestRequest req, ForestMethod method, Object[] args) {
        // req 为Forest请求对象，即 ForestRequest 类实例
        // method 为Forest方法对象，即 ForestMethod 类实例
        // addAttribute作用是添加和Forest请求对象以及该拦截器绑定的属性
    }

    /**
     * 在请求体数据序列化后，发送请求数据前调用该方法
     * 默认为什么都不做
     * 注: multlipart/data类型的文件上传格式的 Body 数据不会调用该回调函数
     *
     * @param request Forest请求对象
     * @param encoder Forest转换器
     * @param encodedData 序列化后的请求体数据
     */
    public byte[] onBodyEncode(ForestRequest request, ForestEncoder encoder, byte[] encodedData) {
        // request: Forest请求对象
        // encoder: 此次转换请求数据的序列化器
        // encodedData: 序列化后的请求体字节数组
        // 返回的字节数组将替换原有的序列化结果
        // 默认不做任何处理，直接返回参数 encodedData
        return encodedData;
    }


    /**
     * 该方法在请求发送之前被调用, 若返回false则不会继续发送请求
     * @Param request Forest请求对象
     */
    @Override
    public boolean beforeExecute(ForestRequest req) {
        // 执行在发送请求之前处理的代码
        if(cacheService.get(req.getUrl()) <= errorCount) {
            log.info("before Execute {}", req.getUrl());
            return true;  // 继续执行请求返回true
        }

        log.info("before not Execute {}", req.getUrl());
        return false;
    }

    /**
     * 该方法在请求成功响应时被调用
     */
    @Override
    public void onSuccess(T data, ForestRequest req, ForestResponse res) {
        if(cacheService.get(req.getUrl()) <= errorCount) {
            cacheService.success(req.getUrl());
        }
    }

    /**
     * 该方法在请求发送失败时被调用
     */
    @Override
    public void onError(ForestRuntimeException ex, ForestRequest req, ForestResponse res) {
        log.info("onError {}", req.getUrl());
        cacheService.failed(req.getUrl());
    }

    /**
     * 该方法在请求发送之后被调用
     */
    @Override
    public void afterExecute(ForestRequest req, ForestResponse res) {

    }
}
