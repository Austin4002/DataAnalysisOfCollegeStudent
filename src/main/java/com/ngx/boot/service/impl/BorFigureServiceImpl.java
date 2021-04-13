package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.BorFigure;
import com.ngx.boot.mapper.BorFigureMapper;
import com.ngx.boot.service.BorFigureService;
import org.springframework.stereotype.Service;

@Service
public class BorFigureServiceImpl extends ServiceImpl<BorFigureMapper, BorFigure> implements BorFigureService {
}
