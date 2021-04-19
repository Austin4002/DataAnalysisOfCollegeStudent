package com.ngx.boot.vo.survey;

import lombok.Data;

@Data
public class Reading {


    //学生年级
    private String grade;

    //学生阅读年份学期
    private String term;

    //学生月阅读量
    private double reading;


}
