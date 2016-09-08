package my.ssh.initializer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by zzy on 16/3/30.
 */
@Configuration
@EnableScheduling
@ComponentScan({"my.ssh.schedule"})
public class SchedulingConfig {
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//
//    }
}
