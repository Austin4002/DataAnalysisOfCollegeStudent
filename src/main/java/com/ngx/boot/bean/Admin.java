package com.ngx.boot.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Admin {

    private String id;
    // 用户名
    private String name;
    // 密码
    private String password;
    // 头像
    private String avatar;
    // 数据更新时间
    private Date updatetime;

}