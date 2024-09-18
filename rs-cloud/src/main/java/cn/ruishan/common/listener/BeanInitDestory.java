package cn.ruishan.common.listener;

import cn.hutool.log.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * <p>
 * Bean初始化与销毁
 * </p>
 *
 * @author longgang.lei
 * @since 2019-11-15
 */
@Component
public class BeanInitDestory {

	private static final Log log = Log.get(BeanInitDestory.class);

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.database}")
    private String database;

    /**
     * @desc: 创建数据库
     * @author: longgang.lei
     * @time: 2021-03-29 17:41
     */
    @PostConstruct
    public void init() {
//        log.debug("bean inited...");
//        try {
//            Class.forName(driverClassName);//加载注册
//            url = url.replace(database, "mysql");
//            //一开始必须填一个已经存在的数据库
//            Connection conn = DriverManager.getConnection(url, username, password);//建立连接
//            String checkdatabase = "CREATE DATABASE IF NOT EXISTS `" + database + "`";//判断数据库是否存在
//            Statement stat = conn.createStatement();
//            if (stat.execute(checkdatabase)) {
//                log.info("Database [" + database + "] CREATED!");
//                stat.close();
//                conn.close();
//            } else {
//                log.debug("Database [" + database + "] EXISTS!");
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }

    @PreDestroy
    public void destory() {
    	log.info("bean destoryed...");
    }
}
