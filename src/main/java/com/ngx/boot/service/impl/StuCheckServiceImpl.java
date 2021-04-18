package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.StuCheck;
import com.ngx.boot.mapper.StuCheckMapper;
import com.ngx.boot.service.StuCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuCheckServiceImpl extends ServiceImpl<StuCheckMapper, StuCheck> implements StuCheckService {

    @Autowired
    private StuCheckMapper stuCheckMapper;

    @Override
    public double getAvgTimeCount(String stuId) {
        double avgTimeCount = stuCheckMapper.getAvgTimeCount(stuId);
        return avgTimeCount;
    }

    @Override
    public double getAvgTime(String stuId) {
        double avgTime =stuCheckMapper.getAvgTime(stuId);
        return avgTime;
    }

    @Override
    public double getTopTimeCount(String stuId) {
        double topTimeCount = stuCheckMapper.getTopTimeCount(stuId);
        return topTimeCount;
    }

    @Override
    public double getTopTime(String stuId) {
        double topTime = stuCheckMapper.getTopTime(stuId);
        return topTime;
    }

    @Override
    public List<Integer> getAllMaxTime() {
        return stuCheckMapper.getAllMaxTime();
    }

    @Override
    public List<Integer> getAllMaxTimeCount() {
        return stuCheckMapper.getAllMaxTimeCount();
    }
}
