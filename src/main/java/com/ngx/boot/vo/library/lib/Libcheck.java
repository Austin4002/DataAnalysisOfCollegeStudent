package com.ngx.boot.vo.library.lib;


import lombok.Data;

import java.util.List;

@Data
public class Libcheck {

    private Frequents frequency;

    private Times time;

    private List<Treemaps> treeMap;



}
