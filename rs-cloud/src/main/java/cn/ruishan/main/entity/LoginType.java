package cn.ruishan.main.entity;

public interface LoginType {

    /**
     * 空白
     */
    String DEFAULT = "DEFAULT";
    /**
     * 后台登录
     */
    String SYS_ADMIN = "SYS_ADMIN";
    /**
     * 企业登录
     */
    String IOT_CORP = "IOT_CORP";

    /** iac 工商业 **/
    /**
     * 设备
     */
    String IAC_DEV = "IAC_DEV";
    /**
     * 用户统称
     */
    String START_IAC_USR = "IAC_USR";
    /**
     * 用户(APP)
     */
    String IAC_USR_APP = "IAC_USR_APP";
    /**
     * 用户(WEB)
     */
    String IAC_USR_WEB = "IAC_USR_WEB";
    /**
     * 用户(PC)
     */
    String IAC_USR_PC = "IAC_USR_PC";


}
