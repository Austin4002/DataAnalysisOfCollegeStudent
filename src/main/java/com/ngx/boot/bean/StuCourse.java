package com.ngx.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class StuCourse {
    @TableId
    private String id;
    // 专业名称
    private String majorName;
    // 专业年级
    private String majorGrade;
    // 学期
    private String majorTerm;
    // 课程名称
    private String courseName;
    // 数据更新时间
    private Date updatetime;

}