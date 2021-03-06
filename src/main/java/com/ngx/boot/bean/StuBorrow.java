package com.ngx.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class StuBorrow {
    @TableId
    private String id;
    // 学号
    private String stuNo;
    // 性别
    private String stuSex;
    // 姓名
    private String stuName;
    // 书籍编号
    private Integer bookNo;
    //  书名
    private String bookName;
    // 书籍类型
    private String bookType;
    // 借阅时长
    private double borTime;
    // 学期
    private Integer stuTerm;
    // 学年
    private Integer stuYear;
    // 数据更新时间
    private Date updatetime;

}