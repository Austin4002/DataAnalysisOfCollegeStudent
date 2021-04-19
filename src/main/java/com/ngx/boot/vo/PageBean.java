package com.ngx.boot.vo;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author : 牛庚新
 * @date :
 */
@Data
public class PageBean {

    // 当前页
    private int current;
    // 当前页显示的数据
    private int size;
    // 总条数
    private int total;
    // 总页数
    private int pages;
    // 数据
    private List<T> records;

}
