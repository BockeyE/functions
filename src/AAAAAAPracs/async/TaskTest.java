package AAAAAAPracs.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author bockey
 */
@Slf4j
@Component
public class TaskTest {
    // 间隔1秒执行一次
    @Async
    @Scheduled(cron = "0/1 * * * * ?")
    public void method1() {
        log.info("——————————method1 start——————————");
        log.info("——————————method1 end——————————");
    }
}

