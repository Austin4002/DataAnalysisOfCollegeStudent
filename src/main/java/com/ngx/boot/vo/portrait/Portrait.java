package com.ngx.boot.vo.portrait;

import lombok.Data;

import java.util.List;

/**
 * @author : 朱坤
 * @date :
 */
@Data
public class Portrait {
    // 学号
    private String stuId;
    // 姓名
    private String stuName;
    // 性别
    private String stuGender;
    // 年级
    private String stuGrade;
    // 专业
    private String stuMajor;
    // 词条
    private String[] stuTags;
    // GPA
    private List<StuGPA> stuGPA;
    // 学生画像
    private List<StuWord> word;
    // 学生行为
    private StuBehavior stuBehavior;
    // 图书借阅
    private StuBook stuBook;

}
