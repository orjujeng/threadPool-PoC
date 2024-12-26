package com.orjujeng.threadpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;

@SpringBootApplication
@EnableAsync
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        ThreadPoolTaskExecutor executor = context.getBean("executor", ThreadPoolTaskExecutor.class);
        int activeCount = executor.getActiveCount();
        int corePoolSize = executor.getCorePoolSize();
        int maxPoolSize = executor.getMaxPoolSize();
        int poolSize = executor.getPoolSize();
        int queueSize = executor.getThreadPoolExecutor().getQueue().size();
        long completedTaskCount = executor.getThreadPoolExecutor().getCompletedTaskCount();
        HashMap data = new HashMap<String,Integer>();
        data.put("activeCount",activeCount);
        data.put("poolSize",poolSize);
        data.put("corePoolSize",corePoolSize);
        data.put("maxPoolSize",maxPoolSize);
        data.put("queueSize",queueSize);
        data.put("completedTaskCount",completedTaskCount);
        System.out.println(data);
    }
}
