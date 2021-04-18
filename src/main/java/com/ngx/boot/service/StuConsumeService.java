package com.ngx.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ngx.boot.bean.StuConsume;

import java.util.List;

public interface StuConsumeService extends IService<StuConsume> {

    List<StuConsume> getStuNoDisctinct();

    double getAvgConsumeMoneyByNo(String stuNo);

    double getMaxConsumeMoneyByNo(String stuNo);

    Integer getFrequencyConsumeData(String stuNo);

}
