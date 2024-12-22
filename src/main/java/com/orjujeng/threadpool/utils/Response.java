package com.orjujeng.threadpool.utils;

public class Response<T> {
    private T data;
    private String code;
    public Response(String code,T data) {
        this.data = data;
        this.code = code;
    }

    public static <T> Response<T> success(T data){
        return new Response<T>("200",data);
    }

    public static <T> Response<T> error(T errorMessage){
        return new Response<T>("500",errorMessage);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
