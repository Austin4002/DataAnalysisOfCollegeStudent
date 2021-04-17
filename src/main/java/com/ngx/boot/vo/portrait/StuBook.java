package com.ngx.boot.vo.portrait;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author : 朱坤
 * @date :
 */
@Data
@AllArgsConstructor
public class StuBook {

    private List<TreeMap> treeMap;

    private InLibraryTime time;

    private InLibraryFrequency frequency;

    // 每月进入图书馆频次
    private Integer[] monthFre;
}
