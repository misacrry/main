package cn.ruishan.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.ContentType;
import cn.ruishan.common.Constants;
import com.dtflys.forest.Forest;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.http.ForestRequest;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @desc: Http请求工具类
 * @author: longgang.lei
 * @create: 2021-10-04 17:33
 **/
@Slf4j
public class HttpUtil extends cn.hutool.http.HttpUtil {

    public static final String EMPTY_BODY = "{}";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    /**
     * 提取地址域名(带端口)
     *
     * @param url
     * @return
     */
    public static String getHost(String url) {
        if (url == null || url.length() == 0)
            return "";

        int doubleslash = url.indexOf("//");
        if (doubleslash == -1) {
            doubleslash = 0;
        } else {
            doubleslash += 2;
        }
        int end = url.indexOf('/', doubleslash);
        end = end >= 0 ? end : url.length();

        return url.substring(0, end);
    }

    /**
     * 提取地址域名(带端口)
     *
     * @param request
     * @return
     */
    public static String getHost(HttpServletRequest request) {
        return HttpUtil.getHost(request.getRequestURL().toString());
    }

    /**
     * 获取URL请求参数，返回JSON格式
     * GET请求
     * @param request
     */
    public static String getUrlParams(HttpServletRequest request) {
        String param = "";
        Map<String, String> result = new LinkedHashMap<String, String>();

        if(StrUtil.isNotBlank(request.getQueryString())) {
            param = URLUtil.decode(request.getQueryString(), CharsetUtil.CHARSET_UTF_8);
            String[] params = param.split("&");
            for (String s : params) {
                int index = s.indexOf("=");
                result.put(s.substring(0, index), s.substring(index + 1));
            }
        }

        return JacksonUtil.obj2String(result);
    }

    /**
     * 获取url全路径
     * 包含参数
     * @param url
     * @param request
     * @return
     */
    public static String getUrlWithParam(String url, HttpServletRequest request) {
        return StrUtil.format("{}?{}", url, getUrlParam(request));
    }

    /**
     * 获取URL请求参数，编码转换
     * GET请求
     * a=1&b=2
     * @param request
     */
    public static String getUrlParam(HttpServletRequest request) {
        String param = "";
        if(StrUtil.isNotBlank(request.getQueryString())) {
            param = URLUtil.decode(request.getQueryString(), CharsetUtil.CHARSET_UTF_8);
        }
        return param;
    }

    /**
     * 获取 Body 参数，返回JSON格式
     * @param request
     */
    public static String getBodyParams(final HttpServletRequest request) {
        String contentType = request.getContentType();
        // 默认表单形式
        if(ContentType.isFormUrlEncode(contentType)) {
            Map<String, String> result = new LinkedHashMap<String, String>();
            Enumeration er = request.getParameterNames();
            while (er.hasMoreElements()) {
                String name = (String) er.nextElement();
                String value = request.getParameter(name);
                result.put(name, value);
            }
            return JacksonUtil.obj2String(result);
        } else {
            StringBuilder wholeStr = new StringBuilder();
            try (
                    InputStream is = request.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, CharsetUtil.CHARSET_UTF_8);
                    BufferedReader reader = new BufferedReader(isr)
            ) {
                String str;
                while ((str = reader.readLine()) != null) {
                    wholeStr.append(str);
                }

                if (StrUtil.isEmpty(wholeStr)) {
                    wholeStr.append("{}");
                }
            } catch (IOException e) {
                log.error("获取 Body 参数出现异常", e);
            }

            // 转化成json对象
            return wholeStr.toString();
        }
    }

	/**
     * @desc: 设置缓存时间 2小时
     * @author: longgang.lei
     * @time: 2021-04-06 13:51
     */
    public static void setCacheHeader(HttpServletResponse response) {

        response.setDateHeader("Expires", DateUtil.current() + Constants.STATIC_EXPIRE_TIME);
    }

    /**
     * GET 方式
     * @param url
     * @return
     */
    public static String get(String url) {
        return Forest.get(url).executeAsString();
    }

    /**
     * GET 方式
     * @param url
     * @param map
     * @return
     */
    public static String get(String url, Map<String, Object> map) {
        return Forest.get(url).addQuery(map).executeAsString();
    }

    /**
     * GET 方式 下载
     * @param url
     * @return
     */
    public static byte[] downloadGet(String url) {
        return Forest.get(url).executeAsByteArray();
    }

    /**
     * GET 方式 下载
     * @param url
     * @param parameters
     * @return
     */
    public static byte[] downloadGet(String url, String parameters)  {
        return Forest.get(url).addQuery(parameters).executeAsByteArray();
    }

    /**
     * POST 方式
     * @param url
     * @return
     */
    public static String post(String url) {
        return Forest.post(url).executeAsString();
    }

    /**
     * POST 方式
     * @param url
     * @param map
     * @return
     */
    public static String post(String url, Map<String, Object> map) {
        return Forest.post(url).addBody(map).executeAsString();
    }

    /**
     * POST 方式
     * @param url
     * @param body
     * @return
     */
    public static String post(String url, @Body String body) {
        return Forest.post(url).addBody(body).executeAsString();
    }

    /**
     * POST 方式
     * @param url
     * @param body
     * @return
     */
    public static String post(String url, @Body String body, HttpServletRequest request) {
        ForestRequest req = Forest.post(url).addBody(body);

        String authorization = request.getHeader("Authorization");

        if(StrUtil.isNotBlank(authorization)) {
            req.addHeader("Authorization", authorization);
        }

        req.setContentType(ContentType.JSON.getValue());

        return req.executeAsString();
    }

    /**
     * POST 方式 下载
     * @param url
     * @return
     */
    public static byte[] downloadPost(String url) {
        return Forest.post(url).executeAsByteArray();
    }

    /**
     * POST 方式 下载
     * @param url
     * @param parameters
     * @return
     */
    public static byte[] downloadPost(String url, String parameters) {
        return Forest.post(url).addBody(parameters).executeAsByteArray();
    }
}
