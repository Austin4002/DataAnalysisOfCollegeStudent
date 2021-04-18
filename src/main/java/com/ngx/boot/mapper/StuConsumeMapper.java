package com.ngx.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ngx.boot.bean.StuConsume;

import java.util.List;

public interface StuConsumeMapper extends BaseMapper<StuConsume> {


    List<StuConsume> getStuNoDisctinct();

    double getAvgConsumeMoneyByNo(String stuNo);

    double getMaxConsumeMoneyByNo(String stuNo);
}
