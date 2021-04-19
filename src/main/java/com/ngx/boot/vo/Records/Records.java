package com.ngx.boot.vo.Records;


import lombok.Data;

import java.util.List;

@Data
public class Records {

    private Integer current;

    private Integer size;

    private long total;


    //概况数据集合
    private List<Record> records;



}
