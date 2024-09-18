package cn.ruishan.common;

/**
 * @desc: 系统常量
 * @author: longgang.lei
 * @time: 2021-04-05 13:02
 */
public class Constants {

    /**
     * 默认成功码
     */
    public static final int RESULT_OK_CODE = 200;
    /**
     * 默认失败码
     */
    public static final int RESULT_ERROR_CODE = 400;

    /* 默认状态 */
    public static final boolean DEFAULT_STATUS = false;

    /**
     * 加密前缀
     */
    public static final String ENCRYPT_PREFIX = "www.ruishan.cn";
    /**
     * 加密次数
     */
    public static final int ENCRYPT_NUM = 2;
    /**
     * password加密类型RS
     */
    public static final String PASSWORD_ID_RS = "RS";
    /**
     * password加密类型MD5
     */
    public static final String PASSWORD_ID_MD5 = "MD5";
    /**
     * 审计数据加密密钥
     */
    public static final String SM4_KEY = "FED762A738879F68";


    /**
     * 初始密码
     * Pgcn*123
     */
    public static final String INITIAL_PASSWORD = "Pgcn*123";
    /**
     * 视图位置
     */
    public static final String VIEWPATH = "components";

    /* 其他 */

    /**
     * 静态文件缓存过期时间,单位毫秒
     */
    public static final Long STATIC_EXPIRE_TIME = 60 * 60 * 1000 * 2L;

    /**
     * 存储图片目录
     */
    public static final String PIC = "pic";
    /**
     * 存储文档目录
     */
    public static final String DOC = "doc";

    /**
     * 存储设备目录
     */
    public static final String DEV = "dev";
    /**
     * 存储故障目录
     */
    public static final String FAULT = "fault";
    /**
     * 存储经验目录
     */
    public static final String EXPERT = "expert";
    /**
     * 存储巡检计划，目录
     */
    public static final String INSPECTION = "inspection";
}
