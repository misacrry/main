package cn.ruishan.common.utils.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @desc: 登录缓存存储
 * @author: longgang.lei
 * @create: 2021-10-09 14:16
 **/
@Slf4j
@Service
public class HttpFailedCacheService {

    private LoadingCache<String, Integer> httpFailedCache;

    public HttpFailedCacheService() {
        super();
        httpFailedCache = CacheBuilder.newBuilder().
                expireAfterWrite(5, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String key) {
                return 0;
            }
        });
    }

    public Integer get(String url) {
        try {
            return httpFailedCache.get(url);
        } catch (ExecutionException e) {
        }
        return 0;
    }

    public void failed(String url) {
        Integer count = get(url);
        if(count == null) {
            count = 0;
        }
        count += 1;
        httpFailedCache.put(url, count);
    }

    public void success(String url) {
        httpFailedCache.invalidate(url);
    }
}
