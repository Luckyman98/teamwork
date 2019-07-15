package cn.luckybin;

import cn.luckybin.file_system.SaveThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName cn.luckybin.WebMaintenanceApplication
 * @Author liuyunbin
 * @Date 2019/6/24 17:36
 * @Description TODO
 */
@SpringBootApplication
public class WebServerApplication {


    public static void main(String[] args) {
        //定时任务周期(秒)
        final int PERIOD = 10;
        SpringApplication.run(WebServerApplication.class);
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        SaveThread saveThread = new SaveThread();
        executorService.scheduleAtFixedRate(saveThread, 0, PERIOD, TimeUnit.SECONDS);
    }
}
