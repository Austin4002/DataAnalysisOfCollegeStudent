package com.ngx.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ngx.boot.bean.StuBorrow;

import java.util.List;

public interface StuBorrowMapper extends BaseMapper<StuBorrow> {
    List<StuBorrow> getStuNoDisctinct();

    double getAvgBorrowTimeByNo(String stuNo);
}
