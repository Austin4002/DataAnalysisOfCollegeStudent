package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.StuCheck;
import com.ngx.boot.mapper.StuCheckMapper;
import com.ngx.boot.service.StuCheckService;
import org.springframework.stereotype.Service;

@Service
public class StuCheckServiceImpl extends ServiceImpl<StuCheckMapper, StuCheck> implements StuCheckService {
}
