package com.ngx.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ngx.boot.bean.StuScore;

import java.util.List;

public interface StuScoreMapper extends BaseMapper<StuScore> {
    double getAvgGPA(String stuId);

    double getTopGPA(String stuId);

    List<StuScore> getStuNoDistinct();
}
