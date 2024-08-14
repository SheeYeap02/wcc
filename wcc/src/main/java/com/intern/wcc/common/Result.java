package com.intern.wcc.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Return the result, response from server will be encapsulated as this result class
 * @param <T>
 */
@Data
public class Result<T> {

    private Integer code; //1 means success, 0 means fail

    private String msg;

    private T data;

    private Integer total;

    private Map map = new HashMap();

    public static <T> Result<T> success(T object) {
        Result<T> r = new Result<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result r = new Result();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
