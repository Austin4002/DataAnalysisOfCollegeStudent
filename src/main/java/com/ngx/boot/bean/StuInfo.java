package com.ngx.boot.bean;

import lombok.Data;

import java.util.Date;

@Data
public class StuInfo {
    // 学号
    private String stuNo;
    // 姓名
    private String stuName;
    // 性别
    private String stuSex;
    // 年级
    private String stuGrade;
    // 专业
    private String stuMajor;
    // 数据更新时间
    private Date updatetime;

}