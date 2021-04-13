package com.ngx.boot.bean;

import lombok.Data;

import java.util.Date;

@Data
public class StuCheck {
    // 学号
    private String stuNo;
    // 性别
    private String stuSex;
    // 姓名
    private String stuName;
    // 考勤频率
    private Integer stuFrequent;
    // 入馆时长
    private String sumTime;
    // 月
    private Integer stuMonth;
    // 学期
    private Integer stuTerm;
    // 年
    private Integer stuYear;
    // 数据更新时间
    private Date updatetime;

}