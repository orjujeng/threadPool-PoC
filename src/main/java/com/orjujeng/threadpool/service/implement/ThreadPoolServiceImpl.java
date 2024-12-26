package com.orjujeng.threadpool.service.implement;

import com.orjujeng.threadpool.mapper.ThreadMapper;
import com.orjujeng.threadpool.service.ThreadPoolSevice;
import com.orjujeng.threadpool.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Service
public class ThreadPoolServiceImpl implements ThreadPoolSevice {
    @Autowired
    ThreadMapper threadMapper;
    @Autowired
    Executor executor;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Override
    @Async
    public void asyncService() {
        System.out.println("async threadpool executing");
        System.out.println("async threadpool name " + Thread.currentThread().getName());
        int insertResult = 0;
        insertResult = threadMapper.insertThreadLog("asyncPool");
    }

    @Override
    public Response completableFutureService() throws ExecutionException, InterruptedException {
        System.out.println("CompletableFuture pool main thread executing");
        System.out.println("CompletableFuture pool main thread name " + Thread.currentThread().getName());
        CompletableFuture<Integer> step1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("CompletableFuture pool step 1 thread executing");
            System.out.println("CompletableFuture pool step 1 thread name " + Thread.currentThread().getName());
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        },threadPoolTaskExecutor);

        CompletableFuture<Integer> step2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("CompletableFuture pool step 2 thread executing");
            System.out.println("CompletableFuture pool step 2 thread name " + Thread.currentThread().getName());
            int insertResult = threadMapper.insertThreadLog("CompletableFuturePool");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return insertResult;
        },threadPoolTaskExecutor);
        CompletableFuture<Void> allOf = CompletableFuture.allOf(step1,step2);

        CompletableFuture<Integer> resultFuture = allOf.thenApply(v -> {
            Integer result1 = step1.join();
            Integer result2 = step2.join();
            return result1 + result2;
        });

        Integer result = resultFuture.get();
        return Response.success(result);
    }
}
