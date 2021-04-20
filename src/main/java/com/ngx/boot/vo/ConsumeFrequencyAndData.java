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
    // 消费频次最大值
    private Integer fre_bound_max;
    // 消费频次最小值
    private Integer fre_bound_min;
    private List<Integer> amount_data;
    private Integer amount_bound_max;
    private Integer amount_bound_min;
    // 消费峰值
    private Integer peak;


}
