package com.orjujeng.threadpool.threadPoolConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
public class TreadPoolConfig {

//    @Bean
//    public Executor taskExecutor() {
//        return Executors.newFixedThreadPool(5);
//    }

//    @Bean
//    public Executor taskExecutor() {
//        return Executors.newCachedThreadPool();
//    }


//    @Bean
//    public Executor taskExecutor() {
//        return Executors.newSingleThreadExecutor();
//    }

//    @Bean
//    public Executor taskExecutor() {
//        return Executors.newScheduledThreadPool(2);
//    }

    @Bean
    public Executor taskExecutor() {
        return new ThreadPoolExecutor(
            1,  // core thread. always running
            1,  // max thread till the max one when queue up to limit
            60, TimeUnit.SECONDS,  // max thread alive tim
            new LinkedBlockingQueue<>(10),  // queue limit
            new ThreadPoolExecutor.DiscardPolicy()  // refuse policy
    );
    }

    @Bean
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("springboot-thread-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "synctaskExecutor")
    public TaskExecutor synctaskExecutor() {
        return new SyncTaskExecutor();
    }

}
