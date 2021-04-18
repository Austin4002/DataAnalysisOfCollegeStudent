package com.ngx.boot.vo.survey;

import lombok.Data;

import java.util.List;

@Data
public class Survey {

    //各年级占比
    private List<Grade> grade;

    //男女占比
    private List<Gender> gender;

    //专业占比
    private List<Majorss> major;

    //餐厅月营业额
    private List<sConsume> consume;

    //学生月阅读量
    private List<Reading> reading;



}
