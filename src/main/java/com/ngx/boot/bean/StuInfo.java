package com.ngx.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class StuInfo {
    // 学号
    @TableId
    private String stuNo;
    // 姓名
    private String stuName;
    // 性别
    private String stuSex;
    // 年级
    private String stuGrade;
    // 专业
    private String stuMajor;
    // 行为特征
    private String behavior;
    // 消费特征
    private String consume;
    // 学习特征
    private String learn;
    // 成绩特征
    private String score;
    // 数据更新时间
    private Date updatetime;

}