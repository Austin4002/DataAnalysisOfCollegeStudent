package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.StuScore;
import com.ngx.boot.mapper.StuScoreMapper;
import com.ngx.boot.service.StuScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StuScoreServiceImpl extends ServiceImpl<StuScoreMapper, StuScore> implements StuScoreService {

    @Autowired
    private StuScoreMapper stuScoreMapper;

    @Override
    public double getAvgGPA(String stuId) {
        double avgGPA = stuScoreMapper.getAvgGPA(stuId);
        return avgGPA;
    }

    @Override
    public double getTopGPA(String stuId) {
        double topGPA = stuScoreMapper.getTopGPA(stuId);
        return topGPA;
    }

    @Override
    public double getAvgScore(String stuId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("stu_no", stuId);
        List<StuScore> list = stuScoreMapper.selectList(wrapper);
//        double totalScore = 0;
//        list.forEach(item -> {
//            totalScore += Double.parseDouble(item.getOneScore());
//            totalScore += Double.parseDouble(item.getTwoScore());
//            totalScore += Double.parseDouble(item.getThreeScore());
//        });
        AtomicReference<Double> totalScore = new AtomicReference<>((double) 0);
        list.forEach(item -> {
            totalScore.updateAndGet(v -> new Double((double) (v + Double.parseDouble(item.getOneScore()))));
            totalScore.updateAndGet(v -> new Double((double) (v + Double.parseDouble(item.getTwoScore()))));
            totalScore.updateAndGet(v -> new Double((double) (v + Double.parseDouble(item.getThreeScore()))));
        });

        int listSize = list.size() * 3;
        double avgScore = totalScore.get() / listSize;

        return avgScore;
    }

    @Override
    public List<StuScore> getStuNoDistinct() {
        return stuScoreMapper.getStuNoDistinct();
    }
}
