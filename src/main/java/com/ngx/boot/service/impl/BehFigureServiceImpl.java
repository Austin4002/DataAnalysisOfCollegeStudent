package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.BehFigure;
import com.ngx.boot.mapper.BehFigureMapper;
import com.ngx.boot.service.BehFigureService;
import org.springframework.stereotype.Service;

@Service
public class BehFigureServiceImpl extends ServiceImpl<BehFigureMapper, BehFigure> implements BehFigureService {
}
