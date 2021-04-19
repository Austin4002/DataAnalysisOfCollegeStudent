package com.ngx.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class StuScore {
    @TableId
    private String id;
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
    // 第一门课程
    private String oneCourse;
    // 第一门课程的分数
    private Double oneScore;
    // 第二门课程
    private String twoCourse;
    // 第二门课程的分数
    private Double twoScore;
    // 第三门课程
    private String threeCourse;
    // 第三门课程的分数
    private Double threeScore;
    // 学年
    private String stuYear;
    // 学期
    private String stuTerm;
    // 平均学分绩点
    private double stuGpa;
    // 数据更新时间
    private Date updatetime;

}