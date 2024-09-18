package cn.ruishan.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
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
public class SpringEventCheck implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ContextClosedEvent ){
            log.debug(" ***** {} 事件已发生！", event.getClass().getSimpleName());
        }else if(event instanceof ContextRefreshedEvent){
        	log.debug(" ***** {} 事件已发生！", event.getClass().getSimpleName());
        }else if(event instanceof ContextStartedEvent){
        	log.debug(" ***** {} 事件已发生！", event.getClass().getSimpleName());
        }else if(event instanceof ContextStoppedEvent){
        	log.debug(" ***** {} 事件已发生！", event.getClass().getSimpleName());
        }else{
        	log.debug(" ***** {} 事件发生！", event.getClass().getName());
        }
    }
}
