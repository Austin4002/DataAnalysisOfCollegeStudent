package com.ngx.boot.vo.Records;


import lombok.Data;

@Data
public class Record {


    //学号
    private String stuId;
    //姓名
    private String stuName;
    //性别
    private String stuGender;
    //年级
    private String stuGrade;
    //专业
    private String stuMajor;
    //行为特征
    private String[] behavior;
    //消费特征
    private String[] consume;
    //学习特征
    private String[] study;





}
