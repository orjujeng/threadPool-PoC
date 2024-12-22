package com.orjujeng.threadpool.controller;

import com.orjujeng.threadpool.service.ThreadPoolSevice;
import com.orjujeng.threadpool.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RequestMapping("/threadPool")
@Controller
@ResponseBody
public class ThreadPoolController {
    @Autowired
    ThreadPoolSevice threadPoolSevice;
    @Autowired
    ThreadPoolTaskExecutor executor;
    @RequestMapping(value = "/async",method = RequestMethod.GET)
    public Response async() throws ExecutionException, InterruptedException {
        System.out.println("async Main thread name " + Thread.currentThread().getName());
        CompletableFuture result = threadPoolSevice.asyncService();
        return Response.success("controller data" + result.get());
    }

    @RequestMapping(value = "/completableFuture",method = RequestMethod.GET)
    public Response completableFuture() throws ExecutionException, InterruptedException {
        System.out.println("completableFuture Main thread name " + Thread.currentThread().getName());
        return threadPoolSevice.completableFutureService();
    }

    @RequestMapping(value = "/monitor",method = RequestMethod.GET)
    public Response monitor()  {
        int activeCount = executor.getActiveCount();
        int corePoolSize = executor.getCorePoolSize();
        int maxPoolSize = executor.getMaxPoolSize();
        int queueSize = executor.getThreadPoolExecutor().getQueue().size();
        long completedTaskCount = executor.getThreadPoolExecutor().getCompletedTaskCount();
        HashMap data = new HashMap<String,Integer>();
        data.put("activeCount",activeCount);
        data.put("corePoolSize",corePoolSize);
        data.put("maxPoolSize",maxPoolSize);
        data.put("queueSize",queueSize);
        data.put("completedTaskCount",completedTaskCount);
        return Response.success(data);
    }
}
