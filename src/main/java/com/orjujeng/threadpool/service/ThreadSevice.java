package com.orjujeng.threadpool.service;

import com.orjujeng.threadpool.utils.Response;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface ThreadSevice {
    public Response runnableService();

    public Response callableService();

//    public void asyncService();
    public CompletableFuture asyncService();

    public  Response completableFutureService() throws ExecutionException, InterruptedException;
}
