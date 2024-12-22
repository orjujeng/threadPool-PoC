package com.orjujeng.threadpool.service.implement;

import com.orjujeng.threadpool.mapper.ThreadMapper;
import com.orjujeng.threadpool.service.ThreadSevice;
import com.orjujeng.threadpool.threadConfig.ThreadCallable;
import com.orjujeng.threadpool.threadConfig.ThreadRunnable;
import com.orjujeng.threadpool.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Service
public class ThreadServiceImpl implements ThreadSevice {
    @Autowired
    ThreadRunnable threadRunnable;
    @Autowired
    ThreadCallable threadCallable;
    @Autowired
    ThreadMapper threadMapper;
    @Override
    public Response runnableService() {
        System.out.println("Main thread executing");
        Thread insertLog = new Thread(threadRunnable);
        insertLog.start();
        try {
            insertLog.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // insert log
        System.out.println("Main thread Done");
        return Response.success("service response data");
    }

    @Override
    public Response callableService() {
        System.out.println("Main thread executing");
        System.out.println("Main thread name " + Thread.currentThread().getName());
        FutureTask<String> ft = new FutureTask<>(threadCallable);
        Thread insertLog = new Thread(ft);
        // insert log
        insertLog.start();
        try {
            System.out.println(ft.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Main thread Done");
        return Response.success("service response data");
    }

//    @Override
//    @Async
//    public void asyncService()  {
//        System.out.println("async thread executing");
//        System.out.println("async thread name " + Thread.currentThread().getName());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        threadMapper.insertThreadLog("async");
//        System.out.println("async thread Done");
//    }

    @Override
    @Async
    public CompletableFuture<String> asyncService()  {
        System.out.println("async thread executing");
        System.out.println("async thread name " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int insertResult = threadMapper.insertThreadLog("async");
        System.out.println("async thread Done");
        return new CompletableFuture<String>().completedFuture("insertResult "+ insertResult);
    }

    @Override
    public Response completableFutureService() throws ExecutionException, InterruptedException {
        System.out.println("CompletableFuture main thread executing");
        System.out.println("CompletableFuture main thread name " + Thread.currentThread().getName());
        CompletableFuture<Integer> step1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("CompletableFuture step 1 thread executing");
            System.out.println("CompletableFuture step 1 thread name " + Thread.currentThread().getName());
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        });

        CompletableFuture<Integer> step2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("CompletableFuture step 2 thread executing");
            System.out.println("CompletableFuture step 2 thread name " + Thread.currentThread().getName());
            int insertResult = threadMapper.insertThreadLog("CompletableFuture");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return insertResult;
        });
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
