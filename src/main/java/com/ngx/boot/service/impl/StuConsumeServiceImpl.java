package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.StuConsume;
import com.ngx.boot.mapper.StuConsumeMapper;
import com.ngx.boot.service.StuConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuConsumeServiceImpl extends ServiceImpl<StuConsumeMapper, StuConsume> implements StuConsumeService {

    @Autowired
    private StuConsumeMapper stuConsumeMapper;

    @Override
    public List<StuConsume> getStuNoDisctinct() {

        List<StuConsume> stuConsumeList =stuConsumeMapper.getStuNoDisctinct();
        return stuConsumeList;
    }

    @Override
    public double getAvgConsumeMoneyByNo(String stuNo) {
        double consumeMoney=stuConsumeMapper.getAvgConsumeMoneyByNo(stuNo);
        return consumeMoney;
    }

    @Override
    public double getMaxConsumeMoneyByNo(String stuNo) {
        double maxMoney = stuConsumeMapper.getMaxConsumeMoneyByNo(stuNo);
        return maxMoney;
    }

    @Override
    public Integer getFrequencyConsumeData(String stuNo) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("stu_no",stuNo);
        Integer count = stuConsumeMapper.selectCount(wrapper);
        return count;
    }
}
