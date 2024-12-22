package com.orjujeng.threadpool.threadConfig;

import com.orjujeng.threadpool.mapper.ThreadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
@Component
public class ThreadCallable implements Callable<String> {
    @Autowired
    ThreadMapper threadMapper;
    @Override
    public String call() throws Exception {
        System.out.println("callable running");
        System.out.println("callable thread name " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int insertResult = threadMapper.insertThreadLog("CallableLog");
        System.out.println("callablethread thread done");
        return "insertResult" + insertResult;
    }
}
