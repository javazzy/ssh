package my.ssh.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by zzy on 16/3/30.
 */
@Component
public class TestScheduling {

    @Scheduled(cron = "* * * * * ?")
    public void testTask(){

    }

}
