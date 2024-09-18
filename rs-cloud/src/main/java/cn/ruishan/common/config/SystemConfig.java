package cn.ruishan.common.config;

import cn.hutool.setting.Setting;

/**
 * 系统配置
 */
public class SystemConfig {

	public static Setting setting = new Setting(SystemConfig.SYSTEM_CONFIG);

	/**
	 * 系统配置文件名称
	 */
	public final static String SYSTEM_CONFIG = "conf/application.setting";
	/**
	 * 图形文件存储路径
	 */
	public final static String GRAPH_FILE_PATH = "graph-file-path";
	/**
	 * 模型文件存储路径
	 */
	public final static String MODEL_FILE_PATH = "model-file-path";
	/**
	 * mqtt主机地址(含端口号)
	 */
	public final static String MQTT_HOST = "mqtt-host";
	/**
	 * web主机地址(含端口号)
	 */
	public final static String WEB_HOST = "web-host";
	/**
	 * 数据库容量
	 */
	public final static String DBCAPACITY = "dbCapacity";
	/**
	 * 消息告警主机地址(含端口号)
	 */
	public final static String ALARM_MSG_HOST = "alarm-msg-host";
	/**
	 * 脚本文件存储路径
	 */
	public final static String SCRIPT_FILE_PATH = "script-file-path";
	/**
	 * 运维文档路径
	 */
	public final static String DOCUMENT_PATH = "document-path";

}
