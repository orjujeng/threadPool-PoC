package com.orjujeng.threadpool.controller;

import com.orjujeng.threadpool.service.ThreadSevice;
import com.orjujeng.threadpool.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequestMapping("/thread")
@Controller
@ResponseBody
public class ThreadController {
    @Autowired
    ThreadSevice threadSevice;
    @RequestMapping(value = "/runnable",method = RequestMethod.GET)
    public Response runnable() {
        return threadSevice.runnableService();
    }

    @RequestMapping(value = "/callable",method = RequestMethod.GET)
    public Response callable() {
        return threadSevice.callableService();
    }

    @RequestMapping(value = "/async",method = RequestMethod.GET)
    public Response async() throws ExecutionException, InterruptedException {
        System.out.println("async Main thread name " + Thread.currentThread().getName());
        CompletableFuture result = threadSevice.asyncService();
        return Response.success("controller data" + result.get());
    }

    @RequestMapping(value = "/completableFuture",method = RequestMethod.GET)
    public Response completableFuture() throws ExecutionException, InterruptedException {
        System.out.println("completableFuture Main thread name " + Thread.currentThread().getName());
        return threadSevice.completableFutureService();
    }
}
