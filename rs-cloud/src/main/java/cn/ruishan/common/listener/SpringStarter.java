package cn.ruishan.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * spring 容器启动之后执行方法
 * </p>
 *
 * @author longgang.lei
 * @since 2019-11-15
 */
@Slf4j
@Component
public class SpringStarter implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
    	log.debug("spring启动后执行方法...");
    }
}
