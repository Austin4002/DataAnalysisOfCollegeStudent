package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.StuFigure;
import com.ngx.boot.mapper.StuFigureMapper;
import com.ngx.boot.service.StuFigureService;
import org.springframework.stereotype.Service;

@Service
public class StuFigureServiceImpl extends ServiceImpl<StuFigureMapper, StuFigure> implements StuFigureService {
}
