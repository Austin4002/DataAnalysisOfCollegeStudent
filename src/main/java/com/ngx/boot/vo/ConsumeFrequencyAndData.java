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

    private String stuAve;
    private String stuGender;
    private String stuGrade;
    private String stuMajor;

    private List<Integer> fre_data;
    private Integer fre_bound_max;
    private Integer fre_bound_min;
    private List<Integer> amount_data;
    private Integer amount_bound_max;
    private Integer amount_bound_min;
    private Integer peak;


}
