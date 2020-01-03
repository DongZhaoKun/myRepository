package com.dzk.utils;

public class CommonResult<T> {
    private  long code;
    private  String message;
    private  T data;

    public CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public  static <T> CommonResult<T> success(T data){
        return new CommonResult<T>(CommonCode.SUCCESS.getCode(),CommonCode.SUCCESS.getMessage(),data);
    }
    public  static <T> CommonResult<T> success(String message,T data){
        return new CommonResult<T>(CommonCode.SUCCESS.getCode(),message,data);
    }
    public static <T> CommonResult<T> failed(T data){
        return new CommonResult<T>(CommonCode.FAILED.getCode(),CommonCode.FAILED.getMessage(),data);
    }
    public static <T> CommonResult failed(String message,T data){
        return new CommonResult<T>(CommonCode.FAILED.getCode(),message,data);
    }
    public static <T> CommonResult<T> viladateFailed(String message){
        return new CommonResult<T>(CommonCode.VALIDATE_FAILED.getCode(),message,null);
    }
    public static <T> CommonResult<T> viladateFailed(String message,T data){
        return new CommonResult<T>(CommonCode.VALIDATE_FAILED.getCode(),message,data);
    }
    public static <T> CommonResult<T> unauthorized(T data){
        return new CommonResult<T>(CommonCode.UNAUTHORIZED.getCode(),CommonCode.UNAUTHORIZED.getMessage(),data);
    }
    public static <T> CommonResult<T> unauthorized(String message,T data){
        return new CommonResult<T>(CommonCode.UNAUTHORIZED.getCode(),message,data);
    }
    public static <T> CommonResult<T> forbidden(T data){
        return new CommonResult<T>(CommonCode.FORBIDDEN.getCode(),CommonCode.FORBIDDEN.getMessage(),data);
    }
    public static <T> CommonResult<T> forbidden(String message,T data){
        return new CommonResult<T>(CommonCode.FORBIDDEN.getCode(),message,data);
    }
    public long getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
