package com.zly.common.vo.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zly.common.enums.base.CommonExceptionEnum;

import java.beans.Transient;
import java.util.Date;

public class ResultT<T> {
    private int code;
    private String message;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp = new Date();

    public ResultT(){}

    public ResultT(T data) {
        this(CommonExceptionEnum.SUCCESS, data);
    }

    public ResultT(CommonExceptionEnum status){
        this(status, status.getReasonPhrase(),null);
    }

    public ResultT(CommonExceptionEnum status, T data){
        this.code = status.getResponseCode();
        this.message = status.getReasonPhrase();
        this.data = data;
    }

    public ResultT(int code,String message){
        this.code = code;
        this.message = message;
    }

    public ResultT(CommonExceptionEnum status, String message, T data){
        this.code = status.getResponseCode();
        this.message = message;
        this.data = data;
    }

    public ResultT(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public void setResultT(CommonExceptionEnum status) {
        this.code = status.getResponseCode();
        this.message = status.getReasonPhrase();
    }

    public static <T> ResultT<T> success() {
        return success(null);
    }

    public static <T> ResultT<T> success(T data) {
        return success(null, data);
    }

    public static <T> ResultT<T> success(String message, T data) {
        return build(CommonExceptionEnum.SUCCESS, message, data);
    }

    public static <T> ResultT<T> fail(CommonExceptionEnum status) {
        return fail(status, null);
    }

    public static <T> ResultT<T> fail(CommonExceptionEnum status, String message) {
        return fail(status, message, null);
    }

    public static <T> ResultT<T> fail(CommonExceptionEnum status, String message, T data) {
        return build(status, message, data);
    }

    private static <T> ResultT<T> build(CommonExceptionEnum status, String message, T data) {
        if(message == null || "".equals(message)) {
            message = status.getReasonPhrase();
        }
        return new ResultT<>(status, message, data);
    }

    @Transient
    public boolean isSuccess(){
        return this.code == CommonExceptionEnum.SUCCESS.getResponseCode();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
