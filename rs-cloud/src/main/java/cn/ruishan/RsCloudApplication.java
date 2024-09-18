package cn.ruishan;

import com.dtflys.forest.springboot.annotation.ForestScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.TimeZone;

/**
 * @desc: 系统启动
 */
@EnableAsync
@ServletComponentScan(value = "cn.ruishan.*.listener")
@SpringBootApplication
@ForestScan(value = "cn.ruishan.common.client")
public class RsCloudApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(RsCloudApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RsCloudApplication.class);
    }

    @PostConstruct
    void setDefaultTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
