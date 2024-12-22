package com.orjujeng.threadpool.service;

import com.orjujeng.threadpool.utils.Response;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface ThreadPoolSevice {
    public CompletableFuture asyncService();

    public Response completableFutureService() throws ExecutionException, InterruptedException;
}
