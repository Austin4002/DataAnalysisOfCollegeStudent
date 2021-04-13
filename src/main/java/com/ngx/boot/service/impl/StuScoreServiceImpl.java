package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.StuScore;
import com.ngx.boot.mapper.StuScoreMapper;
import com.ngx.boot.service.StuScoreService;
import org.springframework.stereotype.Service;

@Service
public class StuScoreServiceImpl extends ServiceImpl<StuScoreMapper, StuScore> implements StuScoreService {
}
