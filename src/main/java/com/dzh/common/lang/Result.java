package com.dzh.common.lang;

import com.dzh.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private int code;
    private String msg;
    private Object data;
    public static Result success(int code,String msg,Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
    public static Result success(Object data) {  //对于静态方法，其他的静态或非静态方法都可以直接调用它。而对于非静态方法，其他的非静态方法是可以直接调用它的。但是其他静态方法只有通过对象才能调用它。
        return success(200,"操作成功",data);
    }
    public static Result fail(int code,String msg,Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
    public static Result fail(String msg){
        return fail(400,msg,null);
    }
    public static Result fail(String msg, Object data){
        return fail(400,msg,data);
    }
}
