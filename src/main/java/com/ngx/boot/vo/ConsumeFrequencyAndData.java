package com.ngx.boot.vo;

import lombok.Data;

import java.util.List;

/**
 * @author : 朱坤
 * @date :
 */
@Data
public class ConsumeFrequencyAndData {

    private String stuId;
    private String stuName;
    // 消费金额平均值
    private Double stuAve;
    private String stuGender;
    private String stuGrade;
    private String stuMajor;

    private List<Integer> fre_data;
    // 消费频次平均值
    private Double fre_bound_max;
    // 消费频次平均值
    private Double fre_bound_min;
    private List<Integer> amount_data;
    // 消费金额聚类中心点大的值
    private Integer amount_bound_max;
    // 消费金额聚类中心点小的值
    private Integer amount_bound_min;
    // 消费峰值
    private Integer peak;


}
