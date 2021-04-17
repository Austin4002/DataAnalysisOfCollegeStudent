package com.ngx.boot.bean;

import lombok.Data;

import java.util.Date;

@Data
public class StuConsume {
    // 学号
    private String stuNo;
    // 性别
    private String stuSex;
    // 姓名
    private String stuName;
    // 专业
    private String stuMajor;
    // 年级
    private String stuGrade;
    // 消费编号
    private Integer conNo;
    // 消费金额
    private double conMoney;
    // 消费餐厅号
    private String conRestaurant;
    // 消费窗口号
    private String conWindow;
    // 月
    private Integer conMonth;
    // 学期
    private Integer stuTerm;
    // 年
    private Integer stuYear;
    // 数据更新时间
    private Date updatetime;

}