package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.mapper.StuInfoMapper;
import com.ngx.boot.service.StuInfoService;
import org.springframework.stereotype.Service;

@Service
public class StuInfoServiceImpl extends ServiceImpl<StuInfoMapper, StuInfo> implements StuInfoService {
}
