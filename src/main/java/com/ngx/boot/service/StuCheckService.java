package com.ngx.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ngx.boot.bean.StuCheck;

import java.util.List;

public interface StuCheckService extends IService<StuCheck> {
    double getAvgTimeCount(String stuId);

    double getAvgTime(String stuId);

    double getTopTimeCount(String stuId);

    double getTopTime(String stuId);

    List<Integer> getAllMaxTime();

    List<Integer> getAllMaxTimeCount();
}
