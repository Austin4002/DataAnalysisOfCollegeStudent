package com.ngx.boot.vo.library;

import lombok.Data;

import java.util.List;

/**
 * @author : 朱坤
 * @date :
 */

@Data
public class Frequent {
    //各年级入馆频次
    private List<Frequency> frequency;
    //各年级入馆时长
    private List<Time> time;
    //每月入馆频次
    private List<Frequency> total;


    
}
