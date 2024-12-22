package com.orjujeng.threadpool.threadConfig;

import com.orjujeng.threadpool.mapper.ThreadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThreadRunnable implements Runnable{
    @Autowired
    ThreadMapper threadMapper;
    @Override
    public void run() {
        System.out.println("runnable thread running");
        System.out.println("runnable thread name " + Thread.currentThread().getName());
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threadMapper.insertThreadLog("RunnableLog");
        System.out.println("runnable thread done");
    }
}
