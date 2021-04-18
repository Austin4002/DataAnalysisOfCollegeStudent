package com.ngx.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ngx.boot.bean.StuScore;

public interface StuScoreService extends IService<StuScore> {

    double getAvgGPA(String stuId);

    double getTopGPA(String stuId);

    double getAvgScore(String stuId);
}
