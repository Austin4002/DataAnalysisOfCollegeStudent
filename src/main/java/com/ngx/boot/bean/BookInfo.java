package com.ngx.boot.bean;

import lombok.Data;

import java.util.Date;

@Data
public class BookInfo {
    // 书籍编号
    private String bookNo;
    // 书籍名称
    private String bookName;
    // 书籍类型
    private String bookType;
    // 数据更新时间
    private Date update;

}