package com.ngx.boot.vo.score;

import lombok.Data;

import java.util.List;

/**
 * @author : 朱坤
 * @date :
 */
@Data
public class Score {
    //学号
    private String stuId;
    //学生姓名
    private String stuName;
    //专业
    private String stuMajor;
    //年级
    private String stuGrade;
    //总成绩
    private Double totalScore;
    //总成绩超过了多少人，百分数
    private Double totalPercent;
    //平均综合成绩
    private Double aveScore;
    //平均成绩超过了多少人，百分数
    private Double avePercent;
    //平均gpa
    private Double aveGpaScore;
    //平均gpa超过了多少人，百分数
    private Double aveGpaPercent;
    //成绩好的三门课
    private List<GoodGrade> goodGradeList;
    //gpa列表
    private List<GPAList> gpaList;

}
