package com.ngx.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ngx.boot.bean.StuCheck;

import java.util.List;

public interface StuCheckMapper extends BaseMapper<StuCheck> {
    double getAvgTimeCount(String stuId);

    double getAvgTime(String stuId);

    double getTopTimeCount(String stuId);

    double getTopTime(String stuId);

    List<Integer> getAllMaxTime();

    List<Integer> getAllMaxTimeCount();

    List<StuCheck> getStuNoDistinct();
}
