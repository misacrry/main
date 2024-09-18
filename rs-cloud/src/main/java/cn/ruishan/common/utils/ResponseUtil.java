package cn.ruishan.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ResponseUtil {

    public static void write(HttpServletResponse response, Object o) {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(o.toString());
        out.flush();
        out.close();
    }

    /**
     * 设置下载文件的header
     *
     * @param response HttpServletResponse
     * @param fileName 文件名称
     */
    public static void setDownloadHeader(HttpServletResponse response, String fileName) {
        response.setContentType("application/force-download");
        try {
            fileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
    }
}
