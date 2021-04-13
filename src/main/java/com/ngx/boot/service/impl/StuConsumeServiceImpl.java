package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.StuConsume;
import com.ngx.boot.mapper.StuConsumeMapper;
import com.ngx.boot.service.StuConsumeService;
import org.springframework.stereotype.Service;

@Service
public class StuConsumeServiceImpl extends ServiceImpl<StuConsumeMapper, StuConsume> implements StuConsumeService {
}
