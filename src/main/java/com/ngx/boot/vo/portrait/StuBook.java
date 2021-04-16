package com.ngx.boot.vo.portrait;

import lombok.Data;

import java.util.List;

/**
 * @author : 朱坤
 * @date :
 */
@Data
public class StuBook {

    private List<TreeMap> treeMap;

    private InLibraryTime time;

    private InLibraryFrequency frequency;
}
