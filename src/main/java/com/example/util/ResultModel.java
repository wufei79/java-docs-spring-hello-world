package com.example.util;

import lombok.Data;

@Data
public class ResultModel<T> {
    private int code;
    private String message="";
    private String question;
    private T answer;
    public ResultModel(){
    }
    public ResultModel(int code,String message){
        this.code = code;
        this.message = message;
    }
    public ResultModel(int code,String message,String question, T answer){
        this.code = code;
        this.message = message;
        this.question = question;
        this.answer = answer;
    }
    public static <T> ResultModel<T> success(String message,String question,T answer){
        return new ResultModel<T>(0,message,question,answer);
    }
    public static <T> ResultModel<T> success(String question,T answer){
        return new ResultModel<T>(0,"success",question,answer);
    }
    public static <T> ResultModel<T> error(int code,String message){
        return new ResultModel<T>(code,message);
    }
    public static <T> ResultModel<T> error(String message){
        return new ResultModel<T>(-1,message);
    }
    public static <T> ResultModel<T> error(){
        return new ResultModel<T>(-1,"error");
    }
}
