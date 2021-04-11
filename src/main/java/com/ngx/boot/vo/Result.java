package com.ngx.boot.vo;

import lombok.Data;

@Data
public class Result<T> {
    private int code=200;

    private String msg;

    private T data;

    public Result() {
        super();
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
