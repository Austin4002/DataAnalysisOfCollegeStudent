package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.mapper.StuInfoMapper;
import com.ngx.boot.service.StuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuInfoServiceImpl extends ServiceImpl<StuInfoMapper, StuInfo> implements StuInfoService {


    @Autowired
    private StuInfoMapper stuInfoMapper;

    @Override
    public List<String> getGradeNumber() {
        return stuInfoMapper.getGradeNumber();
    }

    @Override
    public List<String> getGenderNumber() {
        return stuInfoMapper.getGenderNumber();
    }
}
